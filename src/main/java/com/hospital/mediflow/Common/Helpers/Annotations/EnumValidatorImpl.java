package com.hospital.mediflow.Common.Helpers.Annotations;

import com.hospital.mediflow.Common.Annotations.ValidateEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class EnumValidatorImpl implements ConstraintValidator<ValidateEnum,String> {
    private List<String> enumValues;
    @Override
    public void initialize(ValidateEnum constraintAnnotation) {
        enumValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && enumValues.contains(value);
    }
}
