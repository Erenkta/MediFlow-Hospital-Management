package com.hospital.mediflow.Common.Helpers.Schedulers;

import com.hospital.mediflow.Audit.Repository.SensitiveBackupRepository;
import com.hospital.mediflow.Common.Configuration.Properties.SchedulerProperties;
import com.hospital.mediflow.Common.Exceptions.BaseException;
import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Common.Helpers.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;



@Component
@RequiredArgsConstructor
public class RetentionScheduler {

    private final ExcelService excelService;
    private final SensitiveBackupRepository repository;

    @Value("${mediflow.sensitive.backup.path}")
    public String excelPath;

    @Scheduled(cron = "${mediflow.scheduler.clean.backup}")
    @Transactional
    public void cleanupOldBackups() {
        try{
           byte[] backupAsBytes= excelService.createBackupExcel();
           excelService.saveToFile(excelPath+"/"+ LocalDate.now(),backupAsBytes);
        } catch (IOException e) {
            throw new BaseException(e.getLocalizedMessage(), ErrorCode.IO_ERROR);
        }
        repository.deleteExpiredBackups();
    }
}