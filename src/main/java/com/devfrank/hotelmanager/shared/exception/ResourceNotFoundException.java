package com.devfrank.hotelmanager.shared.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String id) {
        super("No se encontró el recurso " + resource + " con ID: " + id);
    }
}