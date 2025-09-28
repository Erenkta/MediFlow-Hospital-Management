package com.hospital.mediflow.Doctor.Enums;

public enum TitleEnum {
    INTERN("101"),
    ASSISTANT("201"),
    SPECIALIST("301"),
    PROFESSOR("401");

    private final String value;

    TitleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }}

