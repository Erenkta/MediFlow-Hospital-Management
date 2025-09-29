package com.hospital.mediflow.Doctor.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.StringUtils;

public enum TitleEnum {
    INTERN("101"),
    ASSISTANT("201"),
    SPECIALIST("301"),
    PROFESSOR("401");

    private final String value;

    TitleEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getCapitalizedName(){
        return StringUtils.capitalize(this.name().toLowerCase());
    }

    public String getValue(){
        return value;
    }

    @JsonCreator
    public TitleEnum fromValue(String value){
        for (TitleEnum e : TitleEnum.values()) {
            if (e.getCapitalizedName().equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }
}

