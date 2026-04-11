package com.devfrank.hotelmanager.payments.repository;

import com.devfrank.hotelmanager.payments.entity.Payment;
import com.devfrank.hotelmanager.shared.base.repository.SearchableRepository;

import java.util.UUID;

public interface PaymentRepository extends SearchableRepository<Payment, UUID> {
}