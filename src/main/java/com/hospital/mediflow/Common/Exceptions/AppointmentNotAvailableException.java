package com.hospital.mediflow.Common.Exceptions;

public class AppointmentNotAvailableException extends BaseException{
    public AppointmentNotAvailableException(String message) {
        super(message, ErrorCode.APPOINTMENT_NOT_AVAILABLE);
    }

    public AppointmentNotAvailableException(String message, Throwable cause) {
        super(message, ErrorCode.APPOINTMENT_NOT_AVAILABLE);
    }
}
