package com.hospital.mediflow.MedicalRecords.Domain.Entity;

import com.hospital.mediflow.Common.Entities.BaseEntity;
import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records",schema = "mediflow_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MedicalRecord extends BaseEntity {

    @Id
    @SequenceGenerator(
            name = "department_seq",
            sequenceName = "mediflow_schema.id_generator_seq",
            allocationSize = 10
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "department_seq")
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_MEDREC_DOCTOR")
    )
    private Doctor doctor;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(
            name = "patient_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_MEDREC_PATIENT")
    )
    private Patient patient;

    private String diagnosis;

    private String treatment;

    private String prescription;

    private LocalDateTime recordDate;
}
