package com.hospital.mediflow.Common.Specifications;

import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentFilterDto;
import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentSpecification extends BaseSpecification<Appointment> {

    public static Specification<Appointment> hasPatient(Long patientId) {
        return (root, query, cb) ->
                patientId == null ? null : cb.equal(root.get("patient").get("id"), patientId);
    }

    public static Specification<Appointment> hasDoctor(Long doctorId) {
        return (root, query, cb) ->
                doctorId == null ? null : cb.equal(root.get("doctor").get("id"), doctorId);
    }

    public static Specification<Appointment> hasReason(String reason) {
        return (root, query, cb) ->
                (reason == null || reason.isBlank())
                        ? null
                        : cb.like(cb.lower(root.get("reason")), "%" + reason.toLowerCase() + "%");
    }

    public static Specification<Appointment> appointmentDateAfter(LocalDateTime dateAfter) {
        return (root, query, cb) ->
                dateAfter == null ? null : cb.greaterThanOrEqualTo(root.get("appointmentDate"), dateAfter);
    }

    public static Specification<Appointment> appointmentDateBefore(LocalDateTime dateBefore) {
        return (root, query, cb) ->
                dateBefore == null ? null : cb.lessThanOrEqualTo(root.get("appointmentDate"), dateBefore);
    }

    public static Specification<Appointment> hasStatus(List<AppointmentStatusEnum> statuses) {
        return (root, query, cb) ->
                (statuses == null || statuses.isEmpty())
                        ? cb.notEqual(root.get("status"),AppointmentStatusEnum.REJECTED)
                        : root.get("status").in(statuses);
    }
    public static Specification<Appointment> filter(AppointmentFilterDto filter){
        return Specification.allOf(
                hasPatient(filter.patientId()),
                hasDoctor(filter.doctorId()),
                hasReason(filter.reason()),
                appointmentDateAfter(filter.appointmentDateAfter()),
                appointmentDateBefore(filter.appointmentDateBefore()),
                hasStatus(filter.status())
        );
    }

    public static Specification<Appointment> filterAvailableAppointments(Long doctorId,LocalDateTime dateBefore,LocalDateTime dateAfter){
        return Specification.allOf(
                hasDoctor(doctorId),
                appointmentDateAfter(dateAfter),
                appointmentDateBefore(dateBefore)
        );
    }
}
