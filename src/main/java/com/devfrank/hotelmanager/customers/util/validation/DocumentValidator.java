package com.devfrank.hotelmanager.customers.util.validation;

import com.devfrank.hotelmanager.customers.dto.request.SaveCustomerRequest;
import com.devfrank.hotelmanager.customers.util.enums.DocumentType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentValidator implements ConstraintValidator<ValidDocument, SaveCustomerRequest> {

    @Override
    public boolean isValid(SaveCustomerRequest request, ConstraintValidatorContext context) {
        if (request == null || request.documentType() == null || request.documentNumber() == null) {
            return true;
        }

        String type = request.documentType();
        String number = request.documentNumber();

        if (DocumentType.DNI.getValue().equals(type)) {
            return number.matches("^\\d{8}$");
        } else if (DocumentType.CARNET_EXT.getValue().equals(type)) {
            return number.matches("^[a-zA-Z0-9]{8,12}$");
        }

        return false;
    }
}
