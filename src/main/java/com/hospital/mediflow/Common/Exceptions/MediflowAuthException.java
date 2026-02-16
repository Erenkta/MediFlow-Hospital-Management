package com.hospital.mediflow.Common.Exceptions;

public class MediflowAuthException extends BaseException{
    public MediflowAuthException(String message) {
        super(message, ErrorCode.AUTHENTICATION_EXCEPTION);
    }

    public MediflowAuthException(String message, Throwable cause) {
        super(message, ErrorCode.AUTHENTICATION_EXCEPTION);
    }
}
