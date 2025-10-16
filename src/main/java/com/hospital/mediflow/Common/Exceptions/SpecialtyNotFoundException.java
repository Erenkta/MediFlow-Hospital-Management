package com.hospital.mediflow.Common.Exceptions;

public class SpecialtyNotFoundException extends RecordNotFoundException{
    public SpecialtyNotFoundException(String message) {
        super(message);
    }

    public SpecialtyNotFoundException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}
