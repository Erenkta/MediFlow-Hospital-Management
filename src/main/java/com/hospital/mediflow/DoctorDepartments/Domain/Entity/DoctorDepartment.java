package com.hospital.mediflow.DoctorDepartments.Domain.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hospital.mediflow.Common.Entities.BaseEntity;
import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentId;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "doctor_department",schema = "mediflow_schema")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class DoctorDepartment extends BaseEntity {

    @EmbeddedId
    private DoctorDepartmentId id = new DoctorDepartmentId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("departmentId")
    @JsonBackReference

    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("doctorId")
    @JsonBackReference
    private Doctor doctor;

}
