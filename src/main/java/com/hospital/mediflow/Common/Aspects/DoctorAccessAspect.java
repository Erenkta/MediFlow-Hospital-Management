package com.hospital.mediflow.Common.Aspects;

import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class DoctorAccessAspect {
    private final PatientDataService patientDataService;

    @Before("@annotation(com.hospital.mediflow.Common.Annotations.DoctorPatientAccess) && args(patientId,..)")
    public void checkAccess(Long patientId){
        Long doctorId = MediflowUserDetailsService.currentUser().getResourceId();
        boolean isAccessible = patientDataService.isDoctorPatientRelationExists(doctorId,patientId);
        if(!isAccessible){
            throw new AccessDeniedException("Access denied");
        }
    }
}
