package com.hospital.mediflow.Patient.Repository;

import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
