package com.hospital.mediflow.Doctor.Domain.Dtos;

import com.hospital.mediflow.Doctor.Enums.SpecialtyEnum;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;

public record DoctorResponseDto(
                                Integer id,
                                Long doctorCode,
                                TitleEnum title,
                                String firstName,
                                String lastName,
                                SpecialtyEnum specialty,
                                String phone,
                                String email)  {
}
