package com.hospital.mediflow.Common.Annotations;

public enum CountryPhoneFormatEnum {
    TR(11),
    FR(10),
    GE(11),
    UK(11);

    private final int phoneLength;

    CountryPhoneFormatEnum(int phoneLength) {
        this.phoneLength = phoneLength;
    }
    public static int getPhoneLength(String countryCode){
        return CountryPhoneFormatEnum.valueOf(countryCode).phoneLength;
    }
}
