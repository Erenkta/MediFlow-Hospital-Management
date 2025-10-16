package com.hospital.mediflow.Common.Exceptions;

public class RecordNotFoundException extends BaseException{

    public RecordNotFoundException(String message) {
        super(message, ErrorCode.RECORD_NOT_FOUND);
    }

    public RecordNotFoundException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, ErrorCode.RECORD_NOT_FOUND, cause);
    }
}
