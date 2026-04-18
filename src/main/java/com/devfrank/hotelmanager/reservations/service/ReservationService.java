package com.devfrank.hotelmanager.reservations.service;

import com.devfrank.hotelmanager.reservations.dto.ReservationDTO;
import com.devfrank.hotelmanager.reservations.dto.ReservationDetailDTO;
import com.devfrank.hotelmanager.reservations.dto.filter.ReservationCriteria;
import com.devfrank.hotelmanager.reservations.dto.request.CancelReservationRequest;
import com.devfrank.hotelmanager.reservations.dto.request.CreateReservationRequest;
import com.devfrank.hotelmanager.reservations.dto.request.UpdateReservationRequest;
import com.devfrank.hotelmanager.shared.base.service.CreateService;
import com.devfrank.hotelmanager.shared.base.service.UpdateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReservationService extends
        CreateService<CreateReservationRequest, ReservationDTO>,
        UpdateService<UUID, UpdateReservationRequest, ReservationDTO> {

    Page<ReservationDTO> findAllBy(ReservationCriteria filter, Pageable pageable);

    ReservationDetailDTO findById(UUID id);

    ReservationDTO confirm(UUID id);

    void checkIn(UUID id);

    void checkOut(UUID id);

    void cancel(UUID id, CancelReservationRequest request);
}