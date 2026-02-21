package com.hospital.mediflow.Doctor.Services;

import com.hospital.mediflow.Common.Queries.Doctor.DoctorQuery;
import com.hospital.mediflow.Common.Queries.Manager.ManagerDoctorQuery;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import com.hospital.mediflow.Doctor.Services.Abstracts.DoctorService;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DoctorQueryFacade {
    private final DoctorService service;
    private final ManagerDoctorQuery managerQuery;
    private final DoctorQuery doctorQuery;

    public DoctorResponseDto createDoctor(@Valid @RequestBody DoctorRequestDto request){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN -> service.saveDoctor(request);
            case MANAGER -> managerQuery.saveDoctor(request);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public Page<DoctorResponseDto> getDoctors(@NotNull Pageable pageable, DoctorFilterDto filter){
        return service.findDoctors(pageable, filter);
    }
    public List<DoctorResponseDto> getDoctors(DoctorFilterDto filter){
        return service.findDoctors(filter);
    }
    public Page<DoctorResponseDto> getDoctorsByDoctorCode(Pageable pageable, String specialty,TitleEnum title){
        return service.findDoctorsByDoctorCode(pageable, specialty,title);
    }
    public List<DoctorResponseDto> getDoctorsByDoctorCode(String specialty, TitleEnum title){
        return service.findDoctorsByDoctorCode(specialty,title);
    }

    public DoctorResponseDto getDoctorById(Long doctorId){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN,MANAGER -> service.findDoctorById(doctorId);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public DoctorResponseDto updateDoctor(Long doctorId,DoctorRequestDto request){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN -> service.updateDoctor(doctorId,request);
            case MANAGER -> managerQuery.updateDoctor(doctorId,request);
            case DOCTOR ->doctorQuery.updateDoctor(doctorId,request);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public void deleteDoctor(Long doctorId){
        Role role = MediflowUserDetailsService.currentUserRole();
         switch (role) {
            case ADMIN -> service.deleteDoctor(doctorId);
            case MANAGER -> managerQuery.deleteDoctor(doctorId);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        }
    }
}
