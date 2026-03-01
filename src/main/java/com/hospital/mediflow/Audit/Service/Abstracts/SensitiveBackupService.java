package com.hospital.mediflow.Audit.Service.Abstracts;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SensitiveBackupService {
    void backup(Object entity, String entityName, Long entityId, int retentionDays) throws JsonProcessingException;
}
