package com.hospital.mediflow.MedicalRecords.Repository;

import com.hospital.mediflow.Common.BaseRepository;
import com.hospital.mediflow.MedicalRecords.Domain.Entity.MedicalRecord;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends BaseRepository<MedicalRecord,Long>, QuerydslPredicateExecutor<MedicalRecord> {
    List<MedicalRecord> findAll(Predicate predicate);
    Page<MedicalRecord> findAll(Predicate predicate,Pageable pageable);

    @Query("""
    Select Count(r) > 0 From MedicalRecord r where r.id = :record_id and r.patient.id = :patient_id
""")
    boolean isPatientRecordRelationExists(@Param("record_id") Long recordId, @Param("patient_id") Long patientId);

    @Query("""
    Select Count(r) > 0 From MedicalRecord r where r.id = :record_id and r.doctor.id = :doctor_id
""")
    boolean isDoctorRecordRelationExists(@Param("record_id") Long recordId, @Param("doctor_id") Long doctorId);

    @Query("""
    Select Count(r) > 0 From MedicalRecord r 
    left join DoctorDepartment dr on dr.doctor.id = r.doctor.id
    where r.id = :record_id and dr.department.id = :department_id
""")
    boolean isManagerRecordRelationExists(@Param("record_id") Long recordId, @Param("department_id") Long departmentId);

    @Query("""
        select r.doctor.id from MedicalRecord r where r.id = :record_id
""")
    Long findDoctorIdByRecordId(@Param("record_id") Long recordId);
}
