package com.hospital.mediflow.Appointment.Repository;

import com.hospital.mediflow.Appointment.Domain.Dtos.AvailableAppointments;
import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = """
   SELECT * FROM appointments as ap WHERE ap.doctor_id = :id
    """,nativeQuery = true)
    List<Appointment> findAllByDoctorId(@Param("id") Long doctorId);

    @Query(value = """
        SELECT a.appointment_date FROM mediflow_schema.doctors d
        INNER JOIN mediflow_schema.appointments a on d.id = a.doctor_id
        WHERE a.appointment_date between :app_date_start and :app_date_end and d.id = :doctor_id and a.status not in ('REJECTED','DONE');
    """,nativeQuery = true)
    List<Timestamp> findDoctorAppointmentDates(@Param("app_date_start") LocalDateTime appointmentStart,
                                               @Param("app_date_end") LocalDateTime appointmentEnd,
                                               @Param("doctor_id") Long doctorId);


    @Query(value = """
      SELECT
        d.id AS department_id,
        d.name AS department_name,
        COUNT(dd.doctor_id) = 0 AS appointment_available
      FROM mediflow_schema.patients p
      CROSS JOIN mediflow_schema.departments d
      LEFT JOIN mediflow_schema.appointments as a
          ON a.patient_id = p.id
      LEFT JOIN mediflow_schema.doctor_department dd
          ON a.doctor_id = dd.doctor_id AND dd.department_id = d.id
      WHERE p.id = :patient_id and d.id = :department_id
      GROUP BY p.id, p.first_name, d.id, d.name
""",nativeQuery = true)
    AvailableAppointments getDepartmentStatus(@Param("patient_id") Long patientId,@Param("department_id") Long departmentId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = """
    SELECT a from Appointment a where a.id = :appointment_id
    """)
    Optional<Appointment> findByIdLocked(@Param("appointment_id") Long id);


    List<Appointment> findAll(Specification<Appointment> specification);
    Page<Appointment> findAll(Specification<Appointment> specification,Pageable pageable);
}
