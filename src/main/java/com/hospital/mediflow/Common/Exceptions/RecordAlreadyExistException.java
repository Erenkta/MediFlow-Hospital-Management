package com.hospital.mediflow.Common.Exceptions;

public class RecordAlreadyExistException extends BaseException{

    public RecordAlreadyExistException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public RecordAlreadyExistException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}
