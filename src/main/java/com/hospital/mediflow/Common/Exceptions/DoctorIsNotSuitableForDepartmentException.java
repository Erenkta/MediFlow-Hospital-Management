package com.hospital.mediflow.Common.Exceptions;

public class DoctorIsNotSuitableForDepartmentException extends BaseException{
    public DoctorIsNotSuitableForDepartmentException(String message) {
        super(message, ErrorCode.DOCTOR_IS_NOT_SUITABLE_FOR_DEPARTMENT);
    }

    public DoctorIsNotSuitableForDepartmentException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, ErrorCode.DOCTOR_IS_NOT_SUITABLE_FOR_DEPARTMENT, cause);
    }
}
