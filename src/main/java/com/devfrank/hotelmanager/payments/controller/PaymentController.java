package com.devfrank.hotelmanager.payments.controller;

import com.devfrank.hotelmanager.payments.dto.filter.PaymentCriteria;
import com.devfrank.hotelmanager.payments.dto.request.CancelPaymentRequest;
import com.devfrank.hotelmanager.payments.dto.request.CompletePaymentRequest;
import com.devfrank.hotelmanager.payments.dto.request.CreatePaymentRequest;
import com.devfrank.hotelmanager.payments.dto.request.RefundPaymentRequest;
import com.devfrank.hotelmanager.payments.dto.response.PaymentResponse;
import com.devfrank.hotelmanager.payments.service.PaymentService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<PagedResponse<PaymentResponse>> findAll(@Valid PaymentCriteria filter,
                                                                  @PageableDefault Pageable pageable) {
        Page<PaymentResponse> payments = paymentService.findAllBy(filter, pageable)
                .map(PaymentResponse::fromDTO);
        return ResponseEntity.ok(PagedResponse.of(payments));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> findById(@PathVariable UUID id) {
        PaymentResponse payment = PaymentResponse.fromDTO(paymentService.findById(id));
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@RequestBody @Valid CreatePaymentRequest request) {
        PaymentResponse payment = PaymentResponse.fromDTO(paymentService.create(request));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(payment.id())
                .toUri();
        return ResponseEntity.created(uri).body(payment);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<PaymentResponse> complete(@PathVariable UUID id,
                                                    @RequestBody @Valid CompletePaymentRequest request) {
        PaymentResponse payment = PaymentResponse.fromDTO(paymentService.complete(id, request));
        return ResponseEntity.ok(payment);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable UUID id,
                                       @RequestBody @Valid CancelPaymentRequest request) {
        paymentService.cancel(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/refund")
    public ResponseEntity<PaymentResponse> refund(@PathVariable UUID id,
                                                  @RequestBody @Valid RefundPaymentRequest request) {
        PaymentResponse payment = PaymentResponse.fromDTO(paymentService.refund(id, request));
        return ResponseEntity.ok(payment);
    }
}