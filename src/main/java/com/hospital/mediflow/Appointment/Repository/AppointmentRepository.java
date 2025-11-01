package com.hospital.mediflow.Appointment.Repository;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = """
   SELECT * FROM appointments as ap WHERE ap.doctor_id = :id
    """,nativeQuery = true)
    List<Appointment> findAllByDoctorId(@Param("id") Long doctorId);

    List<Appointment> findAll(Specification<Appointment> specification);
    Page<Appointment> findAll(Specification<Appointment> specification,Pageable pageable);
}
