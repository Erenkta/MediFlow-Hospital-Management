package com.hospital.mediflow.DoctorDepartments.Domain.Dtos;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class DoctorDepartmentId implements Serializable {
    private Long doctorId;
    private Long departmentId;

    public DoctorDepartmentId(Long doctorId, Long departmentId){
        this.departmentId = departmentId;
        this.doctorId = doctorId;
    }
}
