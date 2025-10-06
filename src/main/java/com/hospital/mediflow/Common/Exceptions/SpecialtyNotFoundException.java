package com.hospital.mediflow.Common.Exceptions;

public class SpecialtyNotFoundException extends BaseException{
    public SpecialtyNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public SpecialtyNotFoundException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}
