package com.devfrank.hotelmanager.payments.service;

import com.devfrank.hotelmanager.payments.dto.PaymentDTO;
import com.devfrank.hotelmanager.payments.dto.filter.PaymentCriteria;
import com.devfrank.hotelmanager.payments.dto.request.CancelPaymentRequest;
import com.devfrank.hotelmanager.payments.dto.request.CompletePaymentRequest;
import com.devfrank.hotelmanager.payments.dto.request.CreatePaymentRequest;
import com.devfrank.hotelmanager.payments.dto.request.RefundPaymentRequest;
import com.devfrank.hotelmanager.shared.base.service.CreateService;
import com.devfrank.hotelmanager.shared.base.service.ReadService;

import java.util.UUID;

public interface PaymentService extends
        ReadService<PaymentDTO, UUID, PaymentCriteria>,
        CreateService<CreatePaymentRequest, PaymentDTO> {

    PaymentDTO complete(UUID id, CompletePaymentRequest request);

    void cancel(UUID id, CancelPaymentRequest request);

    PaymentDTO refund(UUID id, RefundPaymentRequest request);
}