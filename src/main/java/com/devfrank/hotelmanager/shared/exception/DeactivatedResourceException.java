package com.devfrank.hotelmanager.shared.exception;

import lombok.Getter;

@Getter
public class DeactivatedResourceException extends RuntimeException {
    private final String id;

    public DeactivatedResourceException(String message, String id) {
        super(message);
        this.id = id;
    }
}
