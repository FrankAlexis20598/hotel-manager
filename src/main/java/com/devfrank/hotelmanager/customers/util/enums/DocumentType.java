package com.devfrank.hotelmanager.customers.util.enums;

import lombok.Getter;

@Getter
public enum DocumentType {
    DNI("01"),
    CARNET_EXT("04");

    private final String value;

    DocumentType(String value) {
        this.value = value;
    }

    public static DocumentType fromValue(String value) {
        for (DocumentType type : DocumentType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}