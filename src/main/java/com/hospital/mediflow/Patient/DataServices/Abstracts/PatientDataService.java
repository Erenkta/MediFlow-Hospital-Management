package com.hospital.mediflow.Patient.DataServices.Abstracts;

import com.hospital.mediflow.Patient.Domain.Dtos.PatientRequestDto;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientResponseDto;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PatientDataService {
    List<PatientResponseDto> findAll(Specification<Patient> filterDto);
    Page<PatientResponseDto> findAll(Pageable pageable, Specification<Patient> filterDto);
    PatientResponseDto findById(Long id);
    boolean isDoctorPatientRelationExists(Long doctorId, Long patientId);
    boolean isDepartmentPatientRelationExists(Long departmentId, Long patientId);
    Patient getReferenceById(Long id);
    PatientResponseDto save(PatientRequestDto patientResponseDto);
    PatientResponseDto update(Long id,PatientRequestDto patientResponseDto);
    void deleteById(Long id);
}
