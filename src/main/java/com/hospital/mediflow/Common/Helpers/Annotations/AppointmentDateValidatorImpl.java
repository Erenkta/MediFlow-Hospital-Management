package com.hospital.mediflow.Common.Helpers.Annotations;

import com.hospital.mediflow.Common.Annotations.ValidateAppointmentDate;
import com.hospital.mediflow.Common.Annotations.ValidateBirthDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
public class AppointmentDateValidatorImpl implements ConstraintValidator<ValidateAppointmentDate, LocalDateTime> {
    @Override
    public void initialize(ValidateAppointmentDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime date, ConstraintValidatorContext constraintValidatorContext) {
        final LocalDateTime today = LocalDateTime.now();
        final LocalDateTime oneMonthLater = today.plusMonths(1);
        return date.isBefore(oneMonthLater) && date.isAfter(today);
    }
}
