package com.devfrank.hotelmanager.reservations.service.api;

import java.util.UUID;

public interface CustomerValidation {
    void ensureCustomerIsActive(UUID customerId);
}