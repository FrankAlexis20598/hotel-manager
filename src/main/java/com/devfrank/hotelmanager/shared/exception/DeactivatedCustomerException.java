package com.devfrank.hotelmanager.shared.exception;

import lombok.Getter;

@Getter
public class DeactivatedCustomerException extends RuntimeException {
    private final String customerId;

    public DeactivatedCustomerException(String message, String customerId) {
        super(message);
        this.customerId = customerId;
    }
}