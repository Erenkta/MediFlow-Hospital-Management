package com.hospital.mediflow.Common.Queries.Patient;


import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PatientQuery {
    private final PatientDataService patientDataService;

    public PatientResponseDto findPatientById(Long patientId) {
        Long userPatientId = MediflowUserDetailsService.currentUser().getResourceId();
        boolean isAccessible = userPatientId.equals(patientId);
        if(isAccessible){
            return patientDataService.findById(patientId);
        }
        throw new AccessDeniedException("Access Denied");
    }

    @PreAuthorize("#patientId == authentication.principal.resourceId")
    public PatientResponseDto updatePatient(Long patientId, PatientRequestDto requestDto) {
        return patientDataService.update(patientId,requestDto);
    }

    @PreAuthorize("#patientId == authentication.principal.resourceId")
    public void deletePatient(Long patientId) {
             patientDataService.deleteById(patientId);
    }
}
