package com.hospital.mediflow.Appointment.Domain.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
public record AppointmentFilterDto(
        Long patientId,
        Long doctorId,
        @JsonIgnore Long departmentId,
        String reason,
        LocalDateTime appointmentDateBefore,
        LocalDateTime appointmentDateAfter,
        List<AppointmentStatusEnum> status
){
    public AppointmentFilterDto ManagerFilter(Long departmentId){
        return new AppointmentFilterDto(patientId,doctorId,departmentId,reason,appointmentDateBefore,appointmentDateAfter,status);
    }
    public AppointmentFilterDto DoctorFilter(Long doctorId){
        return new AppointmentFilterDto(patientId,doctorId,departmentId,reason,appointmentDateBefore,appointmentDateAfter,status);
    }
    public AppointmentFilterDto PatientFilter(Long patientId){
        return new AppointmentFilterDto(patientId,doctorId,departmentId,reason,appointmentDateBefore,appointmentDateAfter,status);
    }
}
