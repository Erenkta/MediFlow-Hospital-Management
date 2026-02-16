package com.hospital.mediflow.Appointment.Services;

import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentFilterDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Appointment.Services.Abstracts.AppointmentService;
import com.hospital.mediflow.Common.Providers.Abstracts.CurrentUserProvider;
import com.hospital.mediflow.Common.Queries.Doctor.DoctorAppointmentQuery;
import com.hospital.mediflow.Common.Queries.Manager.ManagerAppointmentQuery;
import com.hospital.mediflow.Common.Queries.Patient.PatientAppointmentQuery;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentQueryFacade {
    private final AppointmentService appointmentService;
    private final CurrentUserProvider userProvider;
    private final ManagerAppointmentQuery managerQuery;
    private final DoctorAppointmentQuery doctorQuery;
    private final PatientAppointmentQuery patientQuery;


    public  List<AppointmentResponseDto> findAll(AppointmentFilterDto filterDto){
        Role role = MediflowUserDetailsService.currentUserRole();
        Long resourceId = userProvider.get().getResourceId();
        return switch (role) {
            case ADMIN   -> appointmentService.findAll(filterDto);
            case MANAGER -> appointmentService.findAll(filterDto.ManagerFilter(resourceId));
            case DOCTOR  -> appointmentService.findAll(filterDto.DoctorFilter(resourceId));
            case PATIENT -> appointmentService.findAll(filterDto.PatientFilter(resourceId));

        };
    }
    public Page<AppointmentResponseDto> findAll(Pageable pageable, AppointmentFilterDto filterDto){
        Role role = MediflowUserDetailsService.currentUserRole();
        Long resourceId = userProvider.get().getResourceId();
        return switch (role) {
            case ADMIN   -> appointmentService.findAll(pageable,filterDto);
            case MANAGER -> appointmentService.findAll(pageable,filterDto.ManagerFilter(resourceId));
            case DOCTOR  -> appointmentService.findAll(pageable,filterDto.DoctorFilter(resourceId));
            case PATIENT -> appointmentService.findAll(pageable,filterDto.PatientFilter(resourceId));
        };
    }

    public AppointmentResponseDto findById(Long id){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case DOCTOR,MANAGER,PATIENT,ADMIN  -> appointmentService.findById(id);
        };
    }

    public List<LocalTime> findAvailableAppointments(Long doctorId,LocalDate appointmentDate){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case DOCTOR,MANAGER,PATIENT,ADMIN  -> appointmentService.getAvailableAppointmentDates(doctorId,appointmentDate);
        };
    }

    public AppointmentResponseDto save(AppointmentRequestDto requestDto){
        Role role = MediflowUserDetailsService.currentUserRole();
        Long resourceId = userProvider.get().getResourceId();
         switch (role) {
            case ADMIN   -> {
                return appointmentService.save(requestDto);
            }
            case MANAGER -> {
                if(!Objects.equals(resourceId, requestDto.departmentId())){
                    throw new AccessDeniedException("Access is denied");
                }
                return appointmentService.save(requestDto);
            }
            case PATIENT -> {
                if(!Objects.equals(resourceId, requestDto.patientId())){
                    throw new AccessDeniedException("Access is denied");
                }
                return appointmentService.save(requestDto);
            }
            default -> throw new AccessDeniedException("Unsupported role for the method");

        }
    }

    public AppointmentResponseDto update(Long id,AppointmentRequestDto requestDto){
        Role role = MediflowUserDetailsService.currentUserRole();
        Long resourceId = userProvider.get().getResourceId();
         switch (role) {
            case ADMIN   -> {
                return appointmentService.update(id, requestDto);
            }
            case MANAGER -> {
                if(!Objects.equals(resourceId, requestDto.departmentId())){
                    throw new AccessDeniedException("Access is denied");
                }
                return appointmentService.update(id, requestDto);
            }
            case DOCTOR  -> {
                if(!Objects.equals(resourceId, requestDto.doctorId())){
                    throw new AccessDeniedException("Access is denied");
                }
                return appointmentService.update(id, requestDto);
            }
            case PATIENT -> {
                if(!Objects.equals(resourceId, requestDto.patientId())){
                    throw new AccessDeniedException("Access is denied");
                }
                return appointmentService.update(id, requestDto);
            }
             default -> throw new AccessDeniedException("Unsupported role for the method");

         }
    }

    public AppointmentResponseDto updateStatus(Long id,AppointmentStatusEnum newStatus){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> appointmentService.updateStatus(id, newStatus);
            case MANAGER -> managerQuery.updateStatus(id, newStatus);
            case DOCTOR  -> doctorQuery.updateStatus(id, newStatus);
            case PATIENT -> patientQuery.updateStatus(id, newStatus);

        };
    }

    public AppointmentResponseDto rescheduleAppointment(Long id,LocalDateTime newDate){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN   -> appointmentService.rescheduleAppointment(id, newDate);
            case MANAGER -> managerQuery.rescheduleAppointment(id, newDate);
            case DOCTOR  -> doctorQuery.rescheduleAppointment(id, newDate);
            case PATIENT -> patientQuery.rescheduleAppointment(id, newDate);
        };
    }

    public void delete(Long id){
        Role role = MediflowUserDetailsService.currentUserRole();
         switch (role) {
             case ADMIN   ->  appointmentService.deleteById(id);
             case MANAGER ->  managerQuery.deleteById(id);
             case PATIENT ->  patientQuery.deleteById(id);
             default -> throw new AccessDeniedException("Unsupported role for the method");
         }
    }
}
