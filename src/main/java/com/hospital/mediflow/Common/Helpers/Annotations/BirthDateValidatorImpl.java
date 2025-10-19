package com.hospital.mediflow.Common.Helpers.Annotations;

import com.hospital.mediflow.Common.Annotations.ValidateBirthDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class BirthDateValidatorImpl implements ConstraintValidator<ValidateBirthDate, Date> {
    @Override
    public void initialize(ValidateBirthDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        final Date today = new Date();
        return !date.after(today); // I didn't use date.before(today) because I want to make valid today
    }
}
