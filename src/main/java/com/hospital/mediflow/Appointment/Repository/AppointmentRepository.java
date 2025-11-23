package com.hospital.mediflow.Appointment.Repository;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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

    List<Appointment> findAll(Specification<Appointment> specification);
    Page<Appointment> findAll(Specification<Appointment> specification,Pageable pageable);
}
