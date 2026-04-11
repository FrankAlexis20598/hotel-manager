package com.devfrank.hotelmanager.shared.util.validation;

import com.devfrank.hotelmanager.shared.constans.ValidationConstants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotBlankIfPresentValidator.class)
public @interface NotBlankIfPresent {
    String message() default ValidationConstants.FIELD_NOT_BLANK_IF_PRESENT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}