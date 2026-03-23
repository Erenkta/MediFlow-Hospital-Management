package com.hospital.mediflow.Audit.Entity;

import com.hospital.mediflow.Common.Entities.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "sensitive_backup")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class SensitiveBackup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityName;
    private Long entityId;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private String backupData;

    private int retentionDays;
}