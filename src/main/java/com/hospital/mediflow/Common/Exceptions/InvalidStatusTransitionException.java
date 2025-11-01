package com.hospital.mediflow.Common.Exceptions;

public class InvalidStatusTransitionException extends BaseException{
    public InvalidStatusTransitionException(String message) {
        super(message, ErrorCode.INVALID_STATUS_TRANSITION);
    }

    public InvalidStatusTransitionException(String message, Throwable cause) {
        super(message, ErrorCode.INVALID_STATUS_TRANSITION);
    }
}
