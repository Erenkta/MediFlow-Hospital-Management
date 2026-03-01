package com.hospital.mediflow.Audit.Repository;

import com.hospital.mediflow.Audit.Entity.SensitiveBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SensitiveBackupRepository extends JpaRepository<SensitiveBackup, Long> {
    void deleteByCreatedAtBefore(LocalDateTime cutoff);
}