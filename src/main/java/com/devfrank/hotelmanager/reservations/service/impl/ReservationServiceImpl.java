package com.devfrank.hotelmanager.reservations.service.impl;

import com.devfrank.hotelmanager.reservations.dto.ReservationDTO;
import com.devfrank.hotelmanager.reservations.dto.ReservationDetailDTO;
import com.devfrank.hotelmanager.reservations.dto.filter.ReservationCriteria;
import com.devfrank.hotelmanager.reservations.dto.request.CancelReservationRequest;
import com.devfrank.hotelmanager.reservations.dto.request.CreateReservationRequest;
import com.devfrank.hotelmanager.reservations.dto.request.UpdateReservationRequest;
import com.devfrank.hotelmanager.reservations.entity.Reservation;
import com.devfrank.hotelmanager.reservations.repository.ReservationRepository;
import com.devfrank.hotelmanager.reservations.service.ReservationService;
import com.devfrank.hotelmanager.reservations.service.api.CustomerValidation;
import com.devfrank.hotelmanager.reservations.service.api.RoomAvailability;
import com.devfrank.hotelmanager.reservations.service.api.RoomStatusManager;
import com.devfrank.hotelmanager.reservations.util.ReservationNumberGenerator;
import com.devfrank.hotelmanager.reservations.util.dto.ReservationExtraInfoDTO;
import com.devfrank.hotelmanager.reservations.util.enums.ReservationStatus;
import com.devfrank.hotelmanager.reservations.util.mapper.ReservationMapper;
import com.devfrank.hotelmanager.reservations.util.specs.ReservationSpecs;
import com.devfrank.hotelmanager.shared.constans.ErrorConstants;
import com.devfrank.hotelmanager.shared.constans.ResourceConstants;
import com.devfrank.hotelmanager.shared.constans.UtilConstants;
import com.devfrank.hotelmanager.shared.exception.BusinessException;
import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;
import com.devfrank.hotelmanager.shared.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final CustomerValidation customerValidation;
    private final RoomAvailability roomAvailability;
    private final ReservationNumberGenerator numberGenerator;
    private final RoomStatusManager roomStatusManager;

    private static final NumberFormat MONEY_FORMAT = NumberFormat.getCurrencyInstance(Locale.of("es", "PE"));
    private static final DateTimeFormatter LOCAL_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(UtilConstants.RESERVATION_LOCAL_DATETIME_PATTERN);

    @Override
    public Page<ReservationDTO> findAllBy(ReservationCriteria filter, Pageable pageable) {
        pageable = PaginationUtils.ensureSort(pageable, UtilConstants.CREATE_AT_ATTRIBUTE, Sort.Direction.DESC);
        Specification<Reservation> specs = ReservationSpecs.filter(filter);
        return reservationRepository.findAll(specs, pageable)
                .map(reservationMapper::toDTO);
    }

    @Override
    public ReservationDetailDTO findById(UUID id) {
        Reservation reservation = findReservationById(id);
        return reservationMapper.toDetailDTO(reservation);
    }

    @Override
    @Transactional
    public ReservationDTO confirm(UUID id) {
        Reservation reservation = findReservationById(id);

        validateStateForConfirmation(reservation);
        validateMinimumPayment(reservation);
        validateRoomAvailabilityForConfirmation(reservation);

        reservation.setStatus(ReservationStatus.CONFIRMADO);
        Reservation savedReservation = reservationRepository.save(reservation);

        return reservationMapper.toDTO(savedReservation);
    }

    @Override
    @Transactional
    public void checkIn(UUID id) {
        Reservation reservation = findReservationById(id);

        validateStateForCheckIn(reservation);
        validateCheckInDate(reservation);

        reservation.setStatus(ReservationStatus.CHECK_IN);
        reservationRepository.save(reservation);

        roomStatusManager.markAsOccupied(id);
    }

    @Override
    @Transactional
    public void checkOut(UUID id) {
        Reservation reservation = findReservationById(id);

        validateStateForCheckOut(reservation);
        validateLateCheckOut(reservation);
        validateRemainingBalance(reservation);

        reservation.setStatus(ReservationStatus.CHECK_OUT);
        reservationRepository.save(reservation);

        roomStatusManager.markAsCleaning(id);
    }

    @Override
    @Transactional
    public void cancel(UUID id, CancelReservationRequest request) {
        Reservation reservation = findReservationById(id);

        validateStateForCancellation(reservation);

        BigDecimal refund = calculateRefundAmount(reservation);

        reservation.setStatus(ReservationStatus.CANCELADO);
        reservation.setCancellationReason(request.cancellationReason());
        reservation.setCancelledAt(LocalDateTime.now());
        reservation.setRefundableAmount(refund);

        roomStatusManager.releaseRoom(reservation.getRoom().getId());

        reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public ReservationDTO create(CreateReservationRequest request) {
        UUID roomId = UUID.fromString(request.roomId());

        customerValidation.ensureCustomerIsActive(UUID.fromString(request.customerId()));

        BigDecimal roomPrice = roomAvailability.verifyAndGetPrice(roomId);

        validateDatesAvailability(roomId, null, request.checkInDate(), request.checkOutDate());

        String reservationNumber = numberGenerator.generateNext(LocalDate.now().getYear());
        ReservationExtraInfoDTO extraInfoDTO = new ReservationExtraInfoDTO(reservationNumber, roomPrice);
        Reservation reservation = reservationMapper.toEntity(request, extraInfoDTO);

        reservation.calculateTotalAmount();

        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationDTO update(UUID id, UpdateReservationRequest request) {
        UUID roomId = UUID.fromString(request.roomId());
        Reservation reservation = findReservationById(id);

        BigDecimal roomPrice = roomAvailability.verifyAndGetPrice(roomId);

        validateDatesAvailability(roomId, id, request.checkInDate(), request.checkOutDate());

        ReservationExtraInfoDTO extraInfoDTO = new ReservationExtraInfoDTO(null, roomPrice);
        reservationMapper.updateEntity(request, extraInfoDTO, reservation);

        reservation.calculateTotalAmount();

        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }

    private Reservation findReservationById(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.RESERVATION, id.toString()));
    }

    private void validateStateForConfirmation(Reservation reservation) {
        if (reservation.getStatus() != ReservationStatus.PENDIENTE) {
            throw new BusinessException(String.format(ErrorConstants.RESERVATION_CONFIRMATION_INVALID_STATE,
                    ReservationStatus.PENDIENTE, reservation.getStatus()));
        }
    }

    private void validateMinimumPayment(Reservation reservation) {
        BigDecimal totalPaid = reservation.getTotalPaid();
        BigDecimal basePrice = reservation.getBasePrice();

        if (totalPaid.compareTo(basePrice) < 0) {
            throw new BusinessException(String.format(ErrorConstants.RESERVATION_ADVANCE_PAYMENT_INVALID,
                    MONEY_FORMAT.format(totalPaid), MONEY_FORMAT.format(basePrice)));
        }
    }

    private void validateRoomAvailabilityForConfirmation(Reservation reservation) {
        if (reservationRepository.existsOverlapForRoomExcludingReservation(reservation.getRoom().getId(),
                reservation.getId(), reservation.getCheckInDate(), reservation.getCheckOutDate())) {
            throw new BusinessException(ErrorConstants.RESERVATION_CONFIRMATION_ROOM_UNAVAILABLE);
        }
    }

    private void validateStateForCheckIn(Reservation reservation) {
        if (reservation.getStatus() != ReservationStatus.CONFIRMADO) {
            throw new BusinessException(String.format(ErrorConstants.RESERVATION_CHECK_IN_INVALID_STATE,
                    ReservationStatus.CONFIRMADO, reservation.getStatus()));
        }
    }

    private void validateCheckInDate(Reservation reservation) {
        LocalDate today = LocalDate.now();
        LocalDate scheduledCheckIn = reservation.getCheckInDate().toLocalDate();
        LocalDate scheduledCheckOut = reservation.getCheckOutDate().toLocalDate();

        if (today.isBefore(scheduledCheckIn)) {
            throw new BusinessException(String.format(ErrorConstants.RESERVATION_CHECK_IN_EARLY, scheduledCheckIn));
        }

        if (!today.isBefore(scheduledCheckOut)) {
            throw new BusinessException(String.format(ErrorConstants.RESERVATION_CHECK_IN_EXPIRED, scheduledCheckOut));
        }
    }

    private void validateStateForCheckOut(Reservation reservation) {
        if (reservation.getStatus() != ReservationStatus.CHECK_IN) {
            throw new BusinessException(String.format(ErrorConstants.RESERVATION_CHECK_OUT_INVALID_STATE,
                    ReservationStatus.CHECK_OUT, reservation.getStatus()));
        }
    }

    private void validateLateCheckOut(Reservation reservation) {
        if (!LocalDate.now().isBefore(reservation.getCheckOutDate().toLocalDate())) {
            LocalTime effectiveLimit = UtilConstants.CHECK_OUT_LIMIT.plusMinutes(UtilConstants.GRACE_MINUTES);
            if (LocalTime.now().isAfter(effectiveLimit)) {
                throw new BusinessException(String.format(ErrorConstants.RESERVATION_CHECK_OUT_LATE,
                        UtilConstants.CHECK_OUT_LIMIT, UtilConstants.GRACE_MINUTES));
            }
        }
    }

    private void validateRemainingBalance(Reservation reservation) {
        if (!reservation.isFullyPaid()) {
            throw new BusinessException(String.format(ErrorConstants.RESERVATION_TOTAL_PAYMENT_INVALID,
                    MONEY_FORMAT.format(reservation.getRemainingBalance())));
        }
    }

    private void validateStateForCancellation(Reservation reservation) {
        if (reservation.getStatus() != ReservationStatus.PENDIENTE
                && reservation.getStatus() != ReservationStatus.CONFIRMADO) {
            throw new BusinessException(String.format(ErrorConstants.RESERVATION_CANCELLATION_INVALID_STATE,
                    ReservationStatus.PENDIENTE, ReservationStatus.CONFIRMADO, reservation.getStatus()));
        }
    }

    private BigDecimal calculateRefundAmount(Reservation reservation) {
        BigDecimal totalPaid = reservation.getTotalPaid();
        if (totalPaid.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        long hoursUntilCheckIn = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getCheckInDate());
        if (hoursUntilCheckIn >= UtilConstants.FREE_CANCELLATION_LIMIT) {
            return totalPaid;
        }
        BigDecimal penalty = reservation.getBasePrice();

        return totalPaid.compareTo(penalty) > 0
                ? totalPaid.subtract(penalty)
                : BigDecimal.ZERO;
    }

    private void validateDatesAvailability(UUID roomId, UUID excludeId, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        boolean hasOverlap = reservationRepository.existsOverlapForRoomExcludingReservation(roomId, excludeId, checkInDate, checkOutDate);
        if (hasOverlap) {
            throw new BusinessException(String.format(ErrorConstants.RESERVATION_OVERLAP,
                    checkInDate.format(LOCAL_DATETIME_FORMATTER), checkOutDate.format(LOCAL_DATETIME_FORMATTER)));
        }
    }
}