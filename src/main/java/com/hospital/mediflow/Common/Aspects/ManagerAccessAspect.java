package com.hospital.mediflow.Common.Aspects;

import com.hospital.mediflow.Common.Resolvers.Doctor.DoctorIdResolver;
import com.hospital.mediflow.Doctor.DataServices.Abstracts.DoctorDataService;
import com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts.DoctorDepartmentDataService;
import com.hospital.mediflow.DoctorDepartments.Domain.Entity.DoctorDepartment;
import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class ManagerAccessAspect {
    private final PatientDataService patientDataService;
    private final DoctorDepartmentDataService DocDepDataService;
    private final DoctorIdResolver doctorIdResolver;

    @Before("@annotation(com.hospital.mediflow.Common.Annotations.Access.Manager.ManagerPatientAccess) && args(patientId,..)")
    public void checkPatientAccess(Long patientId){
        Long departmentId =
                MediflowUserDetailsService.currentUser().getResourceId();

        boolean hasAccess =
                patientDataService.isDepartmentPatientRelationExists(
                        departmentId, patientId
                );

        if (!hasAccess) {
            throw new AccessDeniedException("Access Denied");
        }
    }
    @Before("@annotation(com.hospital.mediflow.Common.Annotations.Access.Manager.ManagerDocDepAccess) && args(departmentId,..)")
    public void checkDocDepAccess(Long departmentId){
        Long managerDepartmentId =
                MediflowUserDetailsService.currentUser().getResourceId();

        boolean hasAccess = managerDepartmentId.equals(departmentId);

        if (!hasAccess) {
            throw new AccessDeniedException("Access Denied");
        }
    }
    @Before("@annotation(com.hospital.mediflow.Common.Annotations.Access.Manager.ManagerRecordAccess)")
    public void checkAccess(JoinPoint joinPoint) {
        Long doctorId = doctorIdResolver.resolve(joinPoint);
        Long departmentId =
                MediflowUserDetailsService.currentUser().getResourceId();

        boolean hasAccess =
                DocDepDataService.isManagerDoctorRelationsExists(
                        doctorId, departmentId
                );

        if (!hasAccess) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}
