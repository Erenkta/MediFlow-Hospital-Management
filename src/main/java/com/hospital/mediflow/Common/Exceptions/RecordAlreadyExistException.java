package com.hospital.mediflow.Common.Exceptions;

public class RecordAlreadyExistException extends BaseException{
    public RecordAlreadyExistException(String message) {
        super(message, ErrorCode.RECORD_ALREADY_EXISTS);
    }

    public RecordAlreadyExistException(String message, Throwable cause) {
        super(message, ErrorCode.RECORD_ALREADY_EXISTS);
    }
}
