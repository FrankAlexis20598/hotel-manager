package com.devfrank.hotelmanager.reservations.controller;

import com.devfrank.hotelmanager.reservations.dto.filter.ReservationCriteria;
import com.devfrank.hotelmanager.reservations.dto.request.CancelReservationRequest;
import com.devfrank.hotelmanager.reservations.dto.request.CreateReservationRequest;
import com.devfrank.hotelmanager.reservations.dto.request.UpdateReservationRequest;
import com.devfrank.hotelmanager.reservations.dto.response.ReservationDetailResponse;
import com.devfrank.hotelmanager.reservations.dto.response.ReservationResponse;
import com.devfrank.hotelmanager.reservations.service.ReservationService;
import com.devfrank.hotelmanager.shared.response.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<PagedResponse<ReservationResponse>> findAll(@Valid ReservationCriteria criteria,
                                                                      @PageableDefault Pageable pageable) {
        Page<ReservationResponse> reservations = reservationService.findAllBy(criteria, pageable)
                .map(ReservationResponse::fromDTO);
        return ResponseEntity.ok(PagedResponse.of(reservations));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDetailResponse> findById(@PathVariable UUID id) {
        ReservationDetailResponse reservationDetail = ReservationDetailResponse.fromDTO(reservationService.findById(id));
        return ResponseEntity.ok(reservationDetail);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody @Valid CreateReservationRequest request) {
        ReservationResponse reservation = ReservationResponse.fromDTO(reservationService.create(request));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservation.id())
                .toUri();
        return ResponseEntity.created(location).body(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> update(@PathVariable UUID id,
                                                      @RequestBody @Valid UpdateReservationRequest request) {
        ReservationResponse reservation = ReservationResponse.fromDTO(reservationService.update(id, request));
        return ResponseEntity.ok(reservation);
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<ReservationResponse> confirm(@PathVariable UUID id) {
        ReservationResponse reservation = ReservationResponse.fromDTO(reservationService.confirm(id));
        return ResponseEntity.ok(reservation);
    }

    @PatchMapping("/{id}/check-in")
    public ResponseEntity<Void> checkIn(@PathVariable UUID id) {
        reservationService.checkIn(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/check-out")
    public ResponseEntity<Void> checkOut(@PathVariable UUID id) {
        reservationService.checkOut(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable UUID id,
                                       @RequestBody @Valid CancelReservationRequest request) {
        reservationService.cancel(id, request);
        return ResponseEntity.noContent().build();
    }
}