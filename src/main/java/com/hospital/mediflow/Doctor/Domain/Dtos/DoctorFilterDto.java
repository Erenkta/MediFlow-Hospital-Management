package com.hospital.mediflow.Doctor.Domain.Dtos;

import com.hospital.mediflow.Doctor.Enums.SpecialtyEnum;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;

import java.util.List;

public record DoctorFilterDto(
   Long id,
   String firstName,
   String lastName,
   List<SpecialtyEnum> specialties,
   TitleEnum title
)
{}
