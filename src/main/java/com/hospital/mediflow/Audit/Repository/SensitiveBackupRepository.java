package com.hospital.mediflow.Audit.Repository;

import com.hospital.mediflow.Audit.Entity.SensitiveBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface SensitiveBackupRepository extends JpaRepository<SensitiveBackup, Long> {
    void deleteByCreatedAtBefore(LocalDateTime cutoff);
    @Query(value = """
            SELECT * FROM mediflow_schema.sensitive_backup s WHERE s.created_at < CURRENT_TIMESTAMP - (s.retention_days || ' days')::interval
            """,nativeQuery = true)
    List<SensitiveBackup> getExpiredBackups();

    @Query(value = """
       DELETE from mediflow_schema.sensitive_backup s WHERE s.created_at < CURRENT_TIMESTAMP - (s.retention_days || ' days')::interval
    """,nativeQuery = true)
    @Modifying
    @Transactional
    void deleteExpiredBackups();
}