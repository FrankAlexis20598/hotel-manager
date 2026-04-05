package com.devfrank.hotelmanager.customers.dto.request;

import com.devfrank.hotelmanager.customers.util.validation.ValidDocument;
import com.devfrank.hotelmanager.shared.constans.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@ValidDocument
public record SaveCustomerRequest(
        @NotBlank(message = ValidationConstants.CUSTOMER_NAMES_NOT_BLANK)
        @Size(min = 2, max = 150, message = ValidationConstants.CUSTOMER_NAMES_SIZE)
        String names,

        @NotBlank(message = ValidationConstants.CUSTOMER_SURNAMES_NOT_BLANK)
        @Size(min = 2, max = 150, message = ValidationConstants.CUSTOMER_SURNAMES_SIZE)
        String surnames,

        @NotBlank(message = ValidationConstants.CUSTOMER_EMAIL_NOT_BLANK)
        @Email(message = ValidationConstants.CUSTOMER_EMAIL_FORMAT)
        @Size(min = 5, max = 150, message = ValidationConstants.CUSTOMER_EMAIL_SIZE)
        String email,

        @Size(min = 9, max = 15, message = ValidationConstants.CUSTOMER_PHONE_SIZE)
        @Pattern(regexp = "^(\\+51)?9\\d{8}$", message = ValidationConstants.CUSTOMER_PHONE_PATTERN)
        String phone,

        @NotBlank(message = ValidationConstants.CUSTOMER_DOCUMENT_TYPE_NOT_BLANK)
        @Size(min = 2, max = 2, message = ValidationConstants.CUSTOMER_DOCUMENT_TYPE_SIZE)
        @Pattern(regexp = "01|04", message = ValidationConstants.CUSTOMER_DOCUMENT_TYPE_PATTERN)
        String documentType,

        @NotBlank(message = ValidationConstants.CUSTOMER_DOCUMENT_NUMBER_NOT_BLANK)
        @Size(min = 8, max = 20, message = ValidationConstants.CUSTOMER_DOCUMENT_NUMBER_SIZE)
        @Pattern(regexp = "^(\\d{8}|[a-zA-Z0-9]{8,12})$", message = ValidationConstants.CUSTOMER_DOCUMENT_NUMBER_PATTERN)
        String documentNumber
) {
}
