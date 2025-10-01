package com.hospital.mediflow.Doctor.Domain.Dtos;

import com.hospital.mediflow.Doctor.Enums.SpecialtyEnum;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record DoctorFilterDto(
   Long id,
   String firstName,
   String lastName,
   List<SpecialtyEnum> specialties,
   TitleEnum title
)
{}
