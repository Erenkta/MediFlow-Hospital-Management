package com.hospital.mediflow.Audit.Service.Concretes;

import com.hospital.mediflow.Audit.Service.Abstracts.AuditLogService;
import com.hospital.mediflow.Audit.Service.Abstracts.SensitiveBackupService;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Audit.Audit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditManagerService {

    private final AuditLogService auditLogService;
    private final SensitiveBackupService backupService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void manageAudit(Audit audit, Object entity){
        try {
            String entityName = audit.returns().getSimpleName();
            String currentUser = (SecurityContextHolder.getContext().getAuthentication() != null)
                    ? SecurityContextHolder.getContext().getAuthentication().getName()
                    : "SYSTEM";

            // Tekil nesneyi listeye çevirerek tek bir döngü üzerinden işlem yapıyoruz
            Collection<?> entities = (entity instanceof Collection<?> col) ? col : Collections.singletonList(entity);

            for (Object obj : entities) {
                Long entityId = getEntityId(obj);
                auditLogService.log(
                        audit.action().name(),
                        entityName,
                        entityId,
                        currentUser,
                        obj
                );
            }
        } catch (Exception ex) {
            log.warn("An error occurred during inserting the audit table. Exception Details: {}", ex.getMessage());
        }
    }

    public  Long getEntityId(Object entity) {
        BeanWrapper wrapper = new BeanWrapperImpl(entity);
        if (wrapper.isReadableProperty("id")) {
            return (Long) wrapper.getPropertyValue("id");
        } else {
            throw new IllegalArgumentException("Entity has no readable 'id' property");
        }
    }
}
