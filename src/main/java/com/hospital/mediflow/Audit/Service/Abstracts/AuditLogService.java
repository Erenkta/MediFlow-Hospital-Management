package com.hospital.mediflow.Audit.Service.Abstracts;

public interface AuditLogService {
    void log(String eventType, String entityName, Long entityId, String performedBy, Object details);
}
