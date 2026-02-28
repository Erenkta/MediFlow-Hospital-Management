package com.hospital.mediflow.Doctor.Services;

import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Annotations.ResourceAccess;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import com.hospital.mediflow.Doctor.Services.Abstracts.DoctorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DoctorQueryFacade {
    private final DoctorService service;

    @ResourceAccess(
            resource = ResourceType.DOCTOR,
            action = AccessType.CREATE,
            payloadParam = "request"
    )
    public DoctorResponseDto createDoctor(@Valid @RequestBody DoctorRequestDto request){
        return service.saveDoctor(request);
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

    @ResourceAccess(
            resource = ResourceType.DOCTOR,
            action = AccessType.READ_BY_ID,
            idParam = "doctorId"
    )
    public DoctorResponseDto getDoctorById(Long doctorId){
        return service.findDoctorById(doctorId);
    }

    @ResourceAccess(
            resource = ResourceType.DOCTOR,
            action = AccessType.UPDATE,
            idParam = "doctorId",
            payloadParam = "request"
    )
    public DoctorResponseDto updateDoctor(Long doctorId,DoctorRequestDto request){
        return service.updateDoctor(doctorId,request);
    }

    @ResourceAccess(
            resource = ResourceType.DOCTOR,
            action = AccessType.DELETE,
            idParam = "doctorId"
    )
    public void deleteDoctor(Long doctorId){
        service.deleteDoctor(doctorId);
    }
}
