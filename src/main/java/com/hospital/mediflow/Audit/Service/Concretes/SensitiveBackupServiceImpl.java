package com.hospital.mediflow.Audit.Service.Concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.mediflow.Audit.Entity.SensitiveBackup;
import com.hospital.mediflow.Audit.Repository.SensitiveBackupRepository;
import com.hospital.mediflow.Audit.Service.Abstracts.SensitiveBackupService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SensitiveBackupServiceImpl implements SensitiveBackupService {

    private final SensitiveBackupRepository repository;
    private final ObjectMapper mapper;

    public SensitiveBackupServiceImpl(SensitiveBackupRepository repository,ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional("auditTransactionManager")
    @Override
    public void backup(Object entity, String entityName, Long entityId, int retentionDays) throws JsonProcessingException {
        SensitiveBackup backup = new SensitiveBackup();
        backup.setEntityName(entityName);
        backup.setEntityId(entityId);
        try {
            backup.setBackupData(mapper.writeValueAsString(entity));
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage());
            throw e;
        }
        backup.setRetentionDays(retentionDays);
        repository.save(backup);
    }
}