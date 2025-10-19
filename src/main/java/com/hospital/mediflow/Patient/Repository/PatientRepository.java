package com.hospital.mediflow.Patient.Repository;

import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAll(Specification<Patient> specification);
}
