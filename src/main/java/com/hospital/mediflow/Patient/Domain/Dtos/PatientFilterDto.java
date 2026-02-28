package com.hospital.mediflow.Patient.Domain.Dtos;

import java.time.LocalDate;
import java.util.List;

public record PatientFilterDto(
        String firstName,
        String lastName,
        Long doctorId,
        Long departmentId,
        LocalDate birthBefore,
        LocalDate birthAfter,
        List<String> bloodGroup,
        String gender
) {
    public PatientFilterDto {
        if (bloodGroup != null && bloodGroup.isEmpty()) {
            bloodGroup = null;
        }
    }
    public PatientFilterDto DoctorFilter(Long doctorId){
        return new PatientFilterDto(firstName,lastName,doctorId,departmentId,birthBefore,birthAfter,bloodGroup,gender);
    }
    public PatientFilterDto ManagerFilter(Long departmentId){
        return new PatientFilterDto(firstName,lastName,doctorId,departmentId,birthBefore,birthAfter,bloodGroup,gender);
    }
}
