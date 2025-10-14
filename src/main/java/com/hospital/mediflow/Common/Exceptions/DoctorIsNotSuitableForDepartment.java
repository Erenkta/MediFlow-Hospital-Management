package com.hospital.mediflow.Common.Exceptions;

public class DoctorIsNotSuitableForDepartment extends BaseException{
    public DoctorIsNotSuitableForDepartment(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public DoctorIsNotSuitableForDepartment(String message, ErrorCode errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}
