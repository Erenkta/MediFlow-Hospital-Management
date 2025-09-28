package com.hospital.mediflow.Common.Exceptions;

public class RecordNotFoundException extends BaseException{

    public RecordNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public RecordNotFoundException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}
