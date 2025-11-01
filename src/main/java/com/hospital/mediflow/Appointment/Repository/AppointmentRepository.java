package com.hospital.mediflow.Appointment.Repository;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
