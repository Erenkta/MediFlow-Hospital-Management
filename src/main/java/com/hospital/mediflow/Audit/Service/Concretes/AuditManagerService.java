package com.hospital.mediflow.Audit.Service.Concretes;

import com.hospital.mediflow.Audit.Service.Abstracts.AuditLogService;
import com.hospital.mediflow.Audit.Service.Abstracts.SensitiveBackupService;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Audit.Audit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditManagerService {

    private final AuditLogService auditLogService;
    private final SensitiveBackupService backupService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void manageAudit(Audit audit, Object entity){
        try {
            Long entityId = getEntityId(entity);
            String entityName = audit.returns().getSimpleName();

            String currentUser =
                    SecurityContextHolder.getContext().getAuthentication() != null
                            ? SecurityContextHolder.getContext().getAuthentication().getName()
                            : "SYSTEM";

            auditLogService.log(
                    audit.action().name(),
                    entityName,
                    entityId,
                    currentUser,
                    entity
            );

        } catch (Exception ex){
            String message = String.format("An error occured during inserting the audit table. Exception Details : %s",ex.getMessage());
            log.warn(message);
        }
    }

    private Long getEntityId(Object entity) throws Exception{
        Method method = entity.getClass().getMethod("id");
        return (Long) method.invoke(entity);
    }
}
