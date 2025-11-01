package com.hospital.mediflow.Appointment.Domain.Entity;

import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Appointment.Enums.States.AppointmentState;
import com.hospital.mediflow.Appointment.Enums.States.ApprovedState;
import com.hospital.mediflow.Appointment.Enums.States.NoActionState;
import com.hospital.mediflow.Appointment.Enums.States.PendingState;
import com.hospital.mediflow.Common.Entities.BaseEntity;
import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Table(name = "appointments",schema = "mediflow_schema")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@Builder(toBuilder = true)
public class Appointment extends BaseEntity {

    @SequenceGenerator(name ="appointment_id_seq",sequenceName = "mediflow_schema.id_generator_seq",allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "appointment_id_seq")
    @Id
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(
            name = "patient_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_APPOINTMENT_PATIENT")
    )
    private Patient patient;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_APPOINTMENT_DOCTOR")
    )
    private Doctor doctor;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    private String reason;

    @Enumerated(EnumType.STRING)
    private AppointmentStatusEnum status;

    @PrePersist
    protected void prePersist() {
        this.status = AppointmentStatusEnum.PENDING;
        String formattedDate = appointmentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        String appointmentRequestMessage = String.format("Appointment request has been created. Requested by : %s , Requested Date : %s, Requested Doctor : %s",patient.getFullName(),formattedDate,doctor.getFullName());
        log.info(appointmentRequestMessage);
    }
    @Transient
    public AppointmentState getState() {
        return switch (status) {
            case PENDING -> new PendingState();
            case APPROVED -> new ApprovedState();
            case REJECTED, DONE -> new NoActionState();
        };
    }
}
