package com.hospital.mediflow.Audit.Entity;

import com.hospital.mediflow.Common.Entities.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "audit_log")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class AuditLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;
    private String entityName;
    private Long entityId;
    private String performedBy;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private String details;

}