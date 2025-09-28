package com.hospital.mediflow.Common.Annotations;

import com.hospital.mediflow.Common.Helpers.Annotations.PhoneValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneValidatorImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatePhone {
    String message() default "Invalid Phone Number Format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
