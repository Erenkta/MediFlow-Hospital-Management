package com.hospital.mediflow.Common.Aspects;

import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class DoctorAccessAspect extends BaseAspect {
    private final PatientDataService patientDataService;
    private final MedicalRecordDataService medicalRecordDataService;

    @Before("@annotation(com.hospital.mediflow.Common.Annotations.Access.Doctor.DoctorPatientAccess) && args(patientId,..)")
    public void checkAccess(Long patientId){
        Long doctorId = MediflowUserDetailsService.currentUser().getResourceId();
        boolean isAccessible = patientDataService.isDoctorPatientRelationExists(doctorId,patientId);
        if(!isAccessible){
            throw new AccessDeniedException("Access denied");
        }
    }
    @Before("@annotation(com.hospital.mediflow.Common.Annotations.Access.Doctor.DoctorRecordAccess) && args(recordId,..)")
    public void checkRecordAccess(Long recordId){
        Long doctorId = MediflowUserDetailsService.currentUser().getResourceId();
        boolean isAccessible = medicalRecordDataService.isDoctorRecordRelationExists(recordId,doctorId);
        if(!isAccessible){
            throw new AccessDeniedException("Access denied");
        }
    }
    @Around("@annotation(com.hospital.mediflow.Common.Annotations.Access.Doctor.AutoFillDoctorId)")
    public Object autoFillPatientFilter(ProceedingJoinPoint pjp) throws Throwable{
        Object[] args = pjp.getArgs();
        Object[] newArgs = args.clone();
        Long resourceId = MediflowUserDetailsService.currentUser().getResourceId();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof MedicalRecordFilterDto filter) {
                newArgs[i] = new MedicalRecordFilterDto(
                        resourceId,
                        filter.patientId(),
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
}
