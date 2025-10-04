package com.hospital.mediflow.Doctor.Services.Abstracts;

import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Enums.SpecialtyEnum;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface DoctorService {
    DoctorResponseDto saveDoctor(DoctorRequestDto requestDto);
    DoctorResponseDto updateDoctor(@NotNull Long id,DoctorRequestDto requestDto);
    DoctorResponseDto findDoctorById(@NotNull Long id);
    List<DoctorResponseDto> findDoctorsByDoctorCode(@Nullable SpecialtyEnum specialty, @Nullable TitleEnum title);
    Page<DoctorResponseDto> findDoctorsByDoctorCode(@Nullable Pageable pageable,@Nullable SpecialtyEnum specialty, @Nullable TitleEnum title);
    List<DoctorResponseDto> findDoctors(DoctorFilterDto filter);
    Page<DoctorResponseDto> findDoctors(@Nullable Pageable pageable, DoctorFilterDto filter);
    void deleteDoctor(@NotNull Long id);

}
