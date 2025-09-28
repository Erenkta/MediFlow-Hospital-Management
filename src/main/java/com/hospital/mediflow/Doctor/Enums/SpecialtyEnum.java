package com.hospital.mediflow.Doctor.Enums;

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

    SpecialtyEnum(String serviceCode){
        this.serviceCode = serviceCode;
    }

    public String getServiceCode(){
        return this.serviceCode;
    }
}
