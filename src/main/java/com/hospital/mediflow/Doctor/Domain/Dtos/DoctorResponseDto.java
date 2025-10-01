package com.hospital.mediflow.Doctor.Domain.Dtos;

import com.hospital.mediflow.Doctor.Enums.SpecialtyEnum;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import lombok.Builder;

@Builder(toBuilder = true)
public record DoctorResponseDto(
                                Long id,
                                Long doctorCode,
                                TitleEnum title,
                                String firstName,
                                String lastName,
                                SpecialtyEnum specialty,
                                String phone,
                                String email)  {
}
