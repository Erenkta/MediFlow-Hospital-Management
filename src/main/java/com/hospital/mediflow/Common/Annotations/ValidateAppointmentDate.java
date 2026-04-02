package com.hospital.mediflow.Common.Annotations;

import com.hospital.mediflow.Common.Helpers.Annotations.AppointmentDateValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AppointmentDateValidatorImpl.class)
public @interface ValidateAppointmentDate {
    String message() default "Invalid appointment Date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
