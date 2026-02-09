package com.hospital.mediflow.Doctor.Services.Concretes;


import com.hospital.mediflow.Doctor.DataServices.Abstracts.DoctorDataService;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import com.hospital.mediflow.Doctor.Services.Abstracts.DoctorService;
import com.hospital.mediflow.DoctorDepartments.Services.Abstracts.DoctorDepartmentService;
import com.hospital.mediflow.Security.Dtos.Entity.User;
import com.hospital.mediflow.Security.Roles.Role;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated // this annotation convert this service to Spring AOP proxy !
public class DoctorServiceImpl implements DoctorService {
    private final DoctorDataService dataService;
    private final DoctorDepartmentService doctorDepartmentService;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:create')")
    public DoctorResponseDto saveDoctor(DoctorRequestDto requestDto) {
        DoctorResponseDto response = dataService.save(requestDto);
        User user = MediflowUserDetailsService.currentUser();
        if(user.getRole().equals(Role.MANAGER)){
            doctorDepartmentService.signDoctorsToDepartment(List.of(response.id()),user.getResourceId());
        }
        return response;
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyAuthority('manager:update','doctor:update')")
    public DoctorResponseDto updateDoctor(@NotNull  Long id, DoctorRequestDto requestDto) {
        Assert.notNull(id,"Doctor id must not be null");
        return dataService.update(id,requestDto);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('manager:read')")
    public DoctorResponseDto findDoctorById(@NotNull Long id) {
        return dataService.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyAuthority('patient:read','doctor:read')")
    public List<DoctorResponseDto> findDoctorsByDoctorCode(String specialty, TitleEnum title) {
        return dataService.findByDoctorCode(specialty,title);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyAuthority('doctor:read')")
    public Page<DoctorResponseDto> findDoctorsByDoctorCode(Pageable pageable, String specialty, TitleEnum title) {
        return dataService.findByDoctorCode(pageable,specialty,title);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyAuthority('patient:read','doctor:read')")
    public List<DoctorResponseDto> findDoctors(DoctorFilterDto filter) {
        return dataService.findAll(filter);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyAuthority('patient:read','doctor:read')")
    public Page<DoctorResponseDto> findDoctors(@Nullable Pageable pageable, DoctorFilterDto filter) {
        return dataService.findAll(pageable,filter);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:delete')")
    public void deleteDoctor(@NotNull Long id) {
        dataService.deleteDoctor(id);
        log.info("Doctor with id {} deleted successfully.", id);
    }
}
