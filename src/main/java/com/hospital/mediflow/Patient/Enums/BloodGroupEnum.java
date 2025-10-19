package com.hospital.mediflow.Patient.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BloodGroupEnum {
    ZERO_NEGATIVE("00RH-"),
    ZERO_POSITIVE("00RH+"),
    A0_NEGATIVE("A0RH-"),
    A0_POSITIVE("A0RH+"),
    B0_NEGATIVE("B0RH-"),
    B0_POSITIVE("B0RH+"),
    AB_NEGATIVE("ABRH-"),
    AB_POSITIVE("ABRH+");

    private final String value;

   BloodGroupEnum(String value){
       this.value = value;
   }

   @JsonValue
    public String getValue(){
       return value;
   }

   @JsonCreator
    public static BloodGroupEnum fromValue(String value){
       for(BloodGroupEnum e : BloodGroupEnum.values()){
           if(e.getValue().equals(value)){
               return e;
           }
       }
       return null;
   }

}
