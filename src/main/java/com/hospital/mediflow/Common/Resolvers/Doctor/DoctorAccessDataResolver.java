package com.hospital.mediflow.Common.Resolvers.Doctor;

import com.hospital.mediflow.Common.Annotations.AccessType;
import com.hospital.mediflow.Common.Aspects.BaseAspect;
import com.hospital.mediflow.Common.Authorization.Model.DoctorAccessData;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;


@Component
public class DoctorAccessDataResolver extends BaseAspect {

    public DoctorAccessData resolve(JoinPoint joinPoint, AccessType type) {
        switch (type) {
            case CREATE -> {
                DoctorRequestDto dto = extract(joinPoint, DoctorRequestDto.class);
                return new DoctorAccessData(dto.specialty(),null,dto.doctorCode());
            }

            case UPDATE, DELETE -> {
                Long doctorId= extract(joinPoint, Long.class);
                return new DoctorAccessData(null,doctorId,null);
            }
            default -> throw new IllegalStateException("Doctor Access Data couldn't resolved properly.");
        }
    }
}
