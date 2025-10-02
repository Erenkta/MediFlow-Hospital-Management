package com.hospital.mediflow.Doctor.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.util.StringUtils;


public enum SpecialtyEnum {
    CARDIOLOGY("001"),
    DERMATOLOGY("002"),
    HEMATOLOGY("003"),
    NEUROLOGY("004"),
    IMMUNOLOGY("005"),
    INTERNAL_MEDICINE("006"),
    EMERGENCY_MEDICINE("007"),
    NEPHROLOGY("008");

    private final String serviceCode;
    private final String value;

    SpecialtyEnum(String serviceCode){
        this.serviceCode = serviceCode;
        this.value = StringUtils.capitalize(this.name().toLowerCase());
    }


    public String getServiceCode(){
        return this.serviceCode;
    }

    @JsonValue
    public String getValue(){
        return value;
    }

    @JsonCreator
    public SpecialtyEnum fromValue(String value){
        for (SpecialtyEnum e : SpecialtyEnum.values()) {
            if (e.getValue().equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }
}
