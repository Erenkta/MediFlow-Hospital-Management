package com.hospital.mediflow.Common.Aspects;

import com.hospital.mediflow.Common.Annotations.Access.Manager.ManagerDoctorAccess;
import com.hospital.mediflow.Common.Providers.Abstracts.CurrentUserProvider;
import com.hospital.mediflow.Common.Resolvers.Doctor.DoctorIdResolver;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts.DoctorDepartmentDataService;
import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Specialty.DataServices.Abstracts.SpecialtyDataService;
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
public class ManagerAccessAspect extends BaseAspect {
    private final PatientDataService patientDataService;
    private final DoctorDepartmentDataService docDepDataService;
    private final DoctorIdResolver doctorIdResolver;
    private final SpecialtyDataService specialtyDataService;
    private final CurrentUserProvider userProvider;

    @Before("@annotation(com.hospital.mediflow.Common.Annotations.Access.Manager.ManagerPatientAccess) && args(patientId,..)")
    public void checkPatientAccess(Long patientId){
        Long departmentId =
                userProvider.get().getResourceId();

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
                userProvider.get().getResourceId();

        boolean hasAccess = managerDepartmentId.equals(departmentId);

        if (!hasAccess) {
            throw new AccessDeniedException("Access Denied");
        }
    }
    @Before("@annotation(com.hospital.mediflow.Common.Annotations.Access.Manager.ManagerRecordAccess)")
    public void checkAccess(JoinPoint joinPoint) {
        Long doctorId = doctorIdResolver.resolve(joinPoint);
        Long departmentId =
                userProvider.get().getResourceId();

        boolean hasAccess =
                docDepDataService.isDepartmentDoctorRelationsExists(
                        doctorId, departmentId
                );

        if (!hasAccess) {
            throw new AccessDeniedException("Access Denied");
        }
    }
    @Before("@annotation(access)")
    public void checkDoctorAccess(JoinPoint jp, ManagerDoctorAccess access) {
        Long departmentId = userProvider.get().getResourceId();

        switch (access.type()) {

            case CREATE -> {
                DoctorRequestDto dto = extract(jp, DoctorRequestDto.class);
                if (!specialtyDataService.isSpecialtyDepartmentRelationExists(dto.specialty(), departmentId)) {
                    throw new AccessDeniedException("Invalid specialty");
                }
            }

            case UPDATE, DELETE -> {
                Long doctorId = extract(jp, Long.class);
                if (!docDepDataService.isDepartmentDoctorRelationsExists(doctorId, departmentId)) {
                    throw new AccessDeniedException("Invalid doctor");
                }
            }
        }
    }
}
