package com.hospital.mediflow.Common.Exceptions;

public class AppointmentNotExistsException extends BaseException{
    public AppointmentNotExistsException(String message) {
        super(message, ErrorCode.APPOINTMENT_NOT_AVAILABLE);
    }

    public AppointmentNotExistsException(String message, Throwable cause) {
        super(message, ErrorCode.APPOINTMENT_NOT_AVAILABLE);
    }
}
