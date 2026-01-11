package com.hospital.mediflow.MedicalRecords.Repository;

import com.hospital.mediflow.Common.BaseRepository;
import com.hospital.mediflow.MedicalRecords.Domain.Entity.MedicalRecord;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends BaseRepository<MedicalRecord,Long>, QuerydslPredicateExecutor<MedicalRecord> {
    List<MedicalRecord> findAll(Predicate predicate);
    Page<MedicalRecord> findAll(Predicate predicate,Pageable pageable);
}
