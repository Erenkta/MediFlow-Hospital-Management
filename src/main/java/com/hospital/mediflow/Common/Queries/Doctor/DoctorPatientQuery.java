package com.hospital.mediflow.Common.Queries.Doctor;

import com.hospital.mediflow.Common.Specifications.PatientSpecification;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientFilterDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import com.hospital.mediflow.Patient.Services.Abstracts.PatientService;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorPatientQuery {
    private final PatientService patientDataService;

    public List<PatientResponseDto> findPatient(PatientFilterDto filter) {
        Long doctorId = MediflowUserDetailsService.currentUser().getResourceId();
        Specification<Patient> spec = PatientSpecification.hasFilter(filter);
        spec = spec.and(PatientSpecification.withDoctorId(doctorId));
        return patientDataService.findAll(spec);
    }
    public Page<PatientResponseDto> findPatient(Pageable pageable,PatientFilterDto filter) {
        Long doctorId = MediflowUserDetailsService.currentUser().getResourceId();
        Specification<Patient> spec = PatientSpecification.hasFilter(filter);
        spec = spec.and(PatientSpecification.withDoctorId(doctorId));
        return patientDataService.findAll(pageable,spec);
    }
}
