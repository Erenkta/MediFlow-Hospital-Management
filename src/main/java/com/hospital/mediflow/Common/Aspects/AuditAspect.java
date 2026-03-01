package com.hospital.mediflow.Common.Aspects;

import com.hospital.mediflow.Audit.Service.Concretes.AuditManagerService;
import com.hospital.mediflow.Common.Annotations.Audit.Audit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class AuditAspect {
    private final AuditManagerService auditManager;

    public AuditAspect(AuditManagerService auditManager) {
        this.auditManager = auditManager;
    }

    @AfterReturning(value = "@annotation(audit)",returning = "entity")
    public void handleAudit(JoinPoint jp, Audit audit, Object entity) {
        auditManager.manageAudit(audit,entity);
    }

}
