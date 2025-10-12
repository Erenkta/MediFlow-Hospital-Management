package com.hospital.mediflow.Doctor.DataServices.Abstracts;

import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DoctorDataService {
    DoctorResponseDto save(DoctorRequestDto requestDto);
    DoctorResponseDto update(Long id,DoctorRequestDto requestDto);
    DoctorResponseDto findById(Long id);
    List<DoctorResponseDto> findByDoctorCode(String specialty, TitleEnum title) ;
    Page<DoctorResponseDto> findByDoctorCode(Pageable pageable,String specialty, TitleEnum title) ;
    Doctor getReferenceById(Long doctorId);
    Page<DoctorResponseDto> findAll(Pageable pageable, DoctorFilterDto filter);
    List<DoctorResponseDto> findAll(DoctorFilterDto filter);
    void deleteDoctor(Long id);
}
