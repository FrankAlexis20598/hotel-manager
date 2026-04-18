package com.devfrank.hotelmanager.payments.service.impl;

import com.devfrank.hotelmanager.payments.dto.PaymentDTO;
import com.devfrank.hotelmanager.payments.dto.filter.PaymentCriteria;
import com.devfrank.hotelmanager.payments.dto.request.CancelPaymentRequest;
import com.devfrank.hotelmanager.payments.dto.request.CompletePaymentRequest;
import com.devfrank.hotelmanager.payments.dto.request.CreatePaymentRequest;
import com.devfrank.hotelmanager.payments.dto.request.RefundPaymentRequest;
import com.devfrank.hotelmanager.payments.entity.Payment;
import com.devfrank.hotelmanager.payments.repository.PaymentRepository;
import com.devfrank.hotelmanager.payments.service.PaymentService;
import com.devfrank.hotelmanager.payments.util.enums.PaymentMethod;
import com.devfrank.hotelmanager.payments.util.enums.PaymentStatus;
import com.devfrank.hotelmanager.payments.util.mapper.PaymentMapper;
import com.devfrank.hotelmanager.payments.util.specs.PaymentSpecs;
import com.devfrank.hotelmanager.shared.constans.ErrorConstants;
import com.devfrank.hotelmanager.shared.constans.ResourceConstants;
import com.devfrank.hotelmanager.shared.exception.BusinessException;
import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDTO complete(UUID id, CompletePaymentRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.PAYMENT, id.toString()));

        if (payment.getStatus() != PaymentStatus.PENDIENTE) {
            throw new BusinessException(String.format(ErrorConstants.PAYMENT_COMPLETION_INVALID_STATE,
                    PaymentStatus.PENDIENTE, payment.getStatus()));
        }

        payment.setObservations(request.observations());
        payment.setPaymentMethod(request.paymentMethod() == null
                ? null
                : PaymentMethod.valueOf(request.paymentMethod()));
        payment.setStatus(PaymentStatus.PAGADO);

        return paymentMapper.toDTO(paymentRepository.save(payment));
    }

    @Override
    public void cancel(UUID id, CancelPaymentRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.PAYMENT, id.toString()));

        if (payment.getStatus() != PaymentStatus.PENDIENTE) {
            throw new BusinessException(String.format(ErrorConstants.PAYMENT_CANCELLATION_INVALID_STATE,
                    PaymentStatus.PENDIENTE, payment.getStatus()));
        }

        payment.setStatusReason(request.reason());
        payment.setStatus(PaymentStatus.ANULADO);

        paymentRepository.save(payment);
    }

    @Override
    public PaymentDTO refund(UUID id, RefundPaymentRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.PAYMENT, id.toString()));

        if (payment.getStatus() != PaymentStatus.PAGADO) {
            throw new BusinessException(String.format(ErrorConstants.PAYMENT_REFUND_INVALID_STATE,
                    PaymentStatus.PAGADO, payment.getStatus()));
        }

        payment.setStatusReason(request.refundReason());
        payment.setStatus(PaymentStatus.REEMBOLSADO);

        return paymentMapper.toDTO(paymentRepository.save(payment));
    }

    @Override
    public PaymentDTO create(CreatePaymentRequest request) {
        Payment payment = paymentMapper.toEntity(request);
        return paymentMapper.toDTO(paymentRepository.save(payment));
    }

    @Override
    public Page<PaymentDTO> findAllBy(PaymentCriteria filter, Pageable pageable) {
        Specification<Payment> specs = PaymentSpecs.filter(filter);
        return paymentRepository.findAll(specs, pageable)
                .map(paymentMapper::toDTO);
    }

    @Override
    public PaymentDTO findById(UUID id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.PAYMENT, id.toString()));
        return paymentMapper.toDTO(payment);
    }
}