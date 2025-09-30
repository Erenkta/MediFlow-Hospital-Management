package com.hospital.mediflow.Common.Helpers.Annotations;

import com.hospital.mediflow.Common.Annotations.ValidateEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class EnumValidatorImpl implements ConstraintValidator<ValidateEnum,Enum> {
    private List<String> enumValues;
    @Override
    public void initialize(ValidateEnum constraintAnnotation) {
        enumValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        return value != null && enumValues.contains(value.name());
    }
}
