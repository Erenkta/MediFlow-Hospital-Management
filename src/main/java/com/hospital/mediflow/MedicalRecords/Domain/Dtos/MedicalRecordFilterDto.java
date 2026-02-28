package com.hospital.mediflow.MedicalRecords.Domain.Dtos;

import java.time.LocalDateTime;

public record MedicalRecordFilterDto(
        Long doctorId,
        Long patientId,
        Long departmentId,
        String doctorName,
        String patientName,
        String departmentName,
        LocalDateTime recordDateStart,
        LocalDateTime recordDateEnd
) {
    public MedicalRecordFilterDto DoctorFilter(Long doctorId) {
        return new MedicalRecordFilterDto(doctorId,patientId,departmentId,doctorName,patientName,departmentName,recordDateStart,recordDateEnd);
    }
    public MedicalRecordFilterDto ManagerFilter(Long departmentId) {
        return new MedicalRecordFilterDto(doctorId,patientId,departmentId,doctorName,patientName,departmentName,recordDateStart,recordDateEnd);
    }
    public MedicalRecordFilterDto PatientFilter(Long patientId) {
        return new MedicalRecordFilterDto(doctorId,patientId,departmentId,doctorName,patientName,departmentName,recordDateStart,recordDateEnd);
    }
}
