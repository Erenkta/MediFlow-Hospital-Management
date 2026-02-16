package com.hospital.mediflow.Common.Aspects;

import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Common.Annotations.Access.Patient.PatientBillingAccess;
import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class PatientAccessAspect extends BaseAspect {
    private final MedicalRecordDataService medicalRecordDataService;
    private final BillingDataService billingDataService;

    @Around("@annotation(com.hospital.mediflow.Common.Annotations.Access.Patient.AutoFillPatientId)")
    public Object autoFillPatientFilter(ProceedingJoinPoint pjp) throws Throwable{
        Object[] args = pjp.getArgs();
        Object[] newArgs = args.clone();
        Long resourceId = MediflowUserDetailsService.currentUser().getResourceId();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof MedicalRecordFilterDto filter) {
                newArgs[i] = new MedicalRecordFilterDto(
                        filter.doctorId(),
                        resourceId,
                        filter.doctorName(),
                        filter.patientName(),
                        filter.departmentName(),
                        filter.recordDateStart(),
                        filter.recordDateEnd()
                );
            }
        }
        return pjp.proceed(newArgs);
    }
    @Before("@annotation(com.hospital.mediflow.Common.Annotations.Access.Patient.PatientRecordAccess) && args(recordId,..)")
    public void checkRecordAccess(Long recordId){
        Long patientId = MediflowUserDetailsService.currentUser().getResourceId();
        boolean isAccessible = medicalRecordDataService.isPatientRecordRelationExists(recordId,patientId);
        if(!isAccessible){
            throw new AccessDeniedException("Access denied");
        }
    }
    @Before("@annotation(access)")
    public void checkBillingAccess(JoinPoint jp,PatientBillingAccess access){
        Long patientId = MediflowUserDetailsService.currentUser().getResourceId();

        switch(access.type()){
            case READ_BY_ID ->{
                Long billingId = extract(jp,Long.class);
                if(!billingDataService.isBillingPatientRelationExists(billingId,patientId)){
                    throw new AccessDeniedException("Access denied");
                }
            }
        }
    }
}
