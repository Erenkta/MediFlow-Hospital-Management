package com.hospital.mediflow.Doctor.Services;

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

    public DoctorResponseDto createDoctor(@Valid @RequestBody DoctorRequestDto request){
        // TODO Check if the requested specialty is owned by the current manager's department
        return service.saveDoctor(request);
    }

    public Page<DoctorResponseDto> getDoctors(@NotNull Pageable pageable, DoctorFilterDto filter){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN,MANAGER -> service.findDoctors(pageable, filter);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }
    public List<DoctorResponseDto> getDoctors(DoctorFilterDto filter){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN,MANAGER -> service.findDoctors(filter);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public Page<DoctorResponseDto> getDoctorsByDoctorCode(Pageable pageable, String specialty,TitleEnum title){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN,MANAGER -> service.findDoctorsByDoctorCode(pageable, specialty,title);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }
    public List<DoctorResponseDto> getDoctorsByDoctorCode(String specialty, TitleEnum title){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN,MANAGER -> service.findDoctorsByDoctorCode(specialty,title);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public DoctorResponseDto getDoctorById(Long id){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN,MANAGER -> service.findDoctorById(id);
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public DoctorResponseDto updateDoctor(Long id,DoctorRequestDto request){
        Role role = MediflowUserDetailsService.currentUserRole();
        return switch (role) {
            case ADMIN -> service.updateDoctor(id,request); // TODO update every information
            case MANAGER -> service.updateDoctor(id,request); // TODO update the specialty and title information. But the manager's department must own the specialty.
            case DOCTOR ->service.updateDoctor(id,request); // TODO update their own information only
            default -> throw new AccessDeniedException("Unsupported role for the method");
        };
    }

    public void deleteDoctor(Long id){
        Role role = MediflowUserDetailsService.currentUserRole();
         switch (role) {
            case ADMIN -> service.deleteDoctor(id);
            case MANAGER -> service.deleteDoctor(id); // TODO can delete the doctor who works on the current manager's department.
            default -> throw new AccessDeniedException("Unsupported role for the method");
        }
    }
}
