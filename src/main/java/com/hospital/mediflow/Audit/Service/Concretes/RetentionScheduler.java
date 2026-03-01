package com.hospital.mediflow.Audit.Service.Concretes;

import com.hospital.mediflow.Audit.Repository.SensitiveBackupRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RetentionScheduler {

    private final SensitiveBackupRepository backupRepository;

    public RetentionScheduler(SensitiveBackupRepository backupRepository) {
        this.backupRepository = backupRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupOldBackups() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        backupRepository.deleteByCreatedAtBefore(cutoff);
    }
}