package com.hospital.mediflow.Common.Exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    RECORD_NOT_FOUND("404"),
    DOCTOR_IS_NOT_SUITABLE_FOR_DEPARTMENT("400"),
    VALIDATION_ERROR("403"),
    ILLEGAL_ARGUMENT("400"),
    METHOD_ARGUMENT_NOT_VALID("400"),
    RECORD_ALREADY_EXISTS("400");

    private final String statusCode;

    ErrorCode(String statusCode){
        this.statusCode = statusCode;
    }
}
