package com.hospital.mediflow.Billing.Repositories;

import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Common.BaseRepository;
import com.hospital.mediflow.Common.Dto.InvoicePdfProjection;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    @Query(
    """
    select b from Billing b where b.appointment.id = :appointment_id and b.appointment.status = 'PENDING' and b.type = 'DEPOSIT'
    """
    )
    Billing findBillingByAppointment(@Param("appointment_id") Long appointmentId);

    @Query(
        """
        Select 
        b.id as id,
        p.firstName as customerName,
        p.lastName as customerLastName,
        b.billingDate as billingDate,
        a.appointmentDate as appointmentDate,
        a.reason as appointmentDescription,
        b.amount as amount
        from Billing b
        inner join Appointment a on b.appointment.id = a.id
        inner join Patient p on b.patient.id = p.id
        
        where b.billingDate >= :start AND b.billingDate < :end
        """
    )
    List<InvoicePdfProjection> findBillingsByDateRanged(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
