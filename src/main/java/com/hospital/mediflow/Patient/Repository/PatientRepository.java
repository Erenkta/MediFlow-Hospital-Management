package com.hospital.mediflow.Patient.Repository;

import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAll(Specification<Patient> specification);
    Page<Patient> findAll(Specification<Patient> spec, Pageable pageable);

    @Query("""
    select count(p) > 0
    from Patient p
    join Appointment a on a.patient.id = p.id
    where p.id = :patientId and a.doctor.id = :doctorId
""")
    boolean isDoctorPatientRelationExists(@Param("doctorId") Long doctorId, @Param("patientId") Long patientId);

    @Query("""
    select count(p) > 0 
    from Patient p
    join Appointment a on a.patient.id = p.id
    join DoctorDepartment dd on dd.doctor.id = a.doctor.id
    where p.id = :patientId and dd.department.id = :departmentId
    """)
    boolean isDepartmentPatientRelationExists(@Param("departmentId") Long departmentId, @Param("patientId") Long patientId);
}
