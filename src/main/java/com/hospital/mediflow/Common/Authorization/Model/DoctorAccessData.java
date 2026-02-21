package com.hospital.mediflow.Common.Authorization.Model;

public record DoctorAccessData(
        String specialty,
        Long doctorId,
        Long doctorCode
) {
}
