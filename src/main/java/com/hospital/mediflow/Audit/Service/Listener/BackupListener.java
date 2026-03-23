package com.hospital.mediflow.Audit.Service.Listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hospital.mediflow.Audit.Event.DomainEvent;
import com.hospital.mediflow.Audit.Service.Abstracts.SensitiveBackupService;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class BackupListener {
    private final SensitiveBackupService backupService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleEntityDeleted(DomainEvent<?> event) throws JsonProcessingException {
        if (AccessType.DELETE == event.action()) {
            backupService.backup(event.entity(),
                    event.entity().getClass().getSimpleName().replace("ResponseDto",""),
                    event.primaryKey(),
                    event.retentionDay() != null ? event.retentionDay() : 30);
        }
    }
}
