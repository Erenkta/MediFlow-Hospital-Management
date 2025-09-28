package com.hospital.mediflow.Common.Helpers.Annotations;

import com.hospital.mediflow.Common.Annotations.CountryPhoneFormatEnum;
import com.hospital.mediflow.Common.Annotations.ValidatePhone;
import jakarta.el.PropertyNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;


@ConditionalOnProperty(prefix = "mediflow", name = "validate.phone",havingValue = "true")
@Slf4j
public class PhoneValidatorImpl implements ConstraintValidator<ValidatePhone,String> {
    @Value("${mediflow.country.code}")
    public String countryCode;

    @Value("${mediflow.country.phone.prefix}")
    public String phonePrefix;

    private Integer countryPhoneLength;

    @Override
    public void initialize(ValidatePhone constraintAnnotation) {
        if(countryCode.isBlank() || phonePrefix.isBlank()){
            log.error("Phone validation is active but country code or phone prefix is not given. Please check the configuration.");
            throw new PropertyNotFoundException("Phone validation is active but country code or phone prefix is not given. Please check the configuration.");
        }
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.countryPhoneLength = CountryPhoneFormatEnum.getPhoneLength(countryCode);

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validatePhoneBasedOnProperties(value);
    }


    public boolean validatePhoneBasedOnProperties(String phoneNumber){
        if(!phoneNumber.startsWith(phonePrefix)) return false;
        return phoneNumber.replace(" ", "").length() == countryPhoneLength;
    }
}
