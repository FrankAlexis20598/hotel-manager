package com.devfrank.hotelmanager.customers.util.converter;

import com.devfrank.hotelmanager.customers.util.enums.DocumentType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DocumentTypeConverter implements AttributeConverter<DocumentType, String> {

    @Override
    public String convertToDatabaseColumn(DocumentType documentType) {
        return documentType != null ? documentType.getValue() : null;
    }

    @Override
    public DocumentType convertToEntityAttribute(String value) {
        return value != null ? DocumentType.fromValue(value) : null;
    }
}