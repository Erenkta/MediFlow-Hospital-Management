package com.hospital.mediflow.Billing.Repositories;

import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Common.BaseRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingRepository extends BaseRepository<Billing,Long>, QuerydslPredicateExecutor<Billing> {

    List<Billing> findAll(Predicate predicate);
    Page<Billing> findAll(Predicate predicate, Pageable pageable);

    @Query("""
            Select count(b) > 0 from Billing b where b.id = :billing_id and b.department.id = :department_id
            """)
    boolean isBillingDepartmentRelationExists(@Param("billing_id") Long billingId, @Param("department_id") Long departmentId);

    @Query("""
            Select count(b) > 0 from Billing b where b.id = :billing_id and b.patient.id = :patient_id
            """)
    boolean isBillingPatientRelationExists(@Param("billing_id") Long billingId, @Param("patient_id") Long patientId);
}
