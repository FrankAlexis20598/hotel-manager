package com.devfrank.hotelmanager.customers.util.validation;

import com.devfrank.hotelmanager.shared.constans.ValidationConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DocumentValidator.class)
public @interface ValidDocument {
    String message() default ValidationConstants.CUSTOMER_DOCUMENT_INVALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}