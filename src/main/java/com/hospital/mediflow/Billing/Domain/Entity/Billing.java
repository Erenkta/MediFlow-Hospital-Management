package com.hospital.mediflow.Billing.Domain.Entity;

import com.hospital.mediflow.Billing.Enums.BillingStatus;
import com.hospital.mediflow.Common.Entities.BaseEntity;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "billing",schema = "mediflow_schema")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Billing extends BaseEntity {

    @Id
    @SequenceGenerator(
            name = "department_seq",
            sequenceName = "mediflow_schema.id_generator_seq",
            allocationSize = 10
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "department_seq")
    private Long id;

    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "patient_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_BILLING_PATIENT")
    )
    private Patient patient;

    @NotNull
    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private BillingStatus status;

    private LocalDateTime billingDate;
}
