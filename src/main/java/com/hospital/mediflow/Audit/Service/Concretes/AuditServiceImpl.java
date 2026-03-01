package com.hospital.mediflow.Audit.Service.Concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.mediflow.Audit.Entity.AuditLog;
import com.hospital.mediflow.Audit.Repository.AuditLogRepository;
import com.hospital.mediflow.Audit.Service.Abstracts.AuditLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuditServiceImpl implements AuditLogService {

    private final AuditLogRepository repository;
    private final ObjectMapper mapper;

    public AuditServiceImpl(AuditLogRepository repository,  ObjectMapper mapper) {
            this.repository = repository;
            this.mapper = mapper;
    }
    @Transactional(value = "auditTransactionManager")
    @Override
    public void log(String eventType, String entityName, Long entityId, String performedBy, Object details) {
        AuditLog logEntity = new AuditLog();
        logEntity.setEventType(eventType);
        logEntity.setEntityName(entityName.replace("ResponseDto",""));
        logEntity.setEntityId(entityId);
        logEntity.setPerformedBy(performedBy);
        try {
            logEntity.setDetails(details != null ? mapper.writeValueAsString(details) : null);
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage());
            logEntity.setDetails("{}");
        }
        repository.save(logEntity);
    }
}
