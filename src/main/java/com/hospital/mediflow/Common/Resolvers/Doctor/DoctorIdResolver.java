package com.hospital.mediflow.Common.Resolvers.Doctor;

import com.hospital.mediflow.Common.Resolvers.Record.RecordResolver;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class DoctorIdResolver {
    private final RecordResolver recordDoctorResolver;

    public DoctorIdResolver(RecordResolver recordDoctorResolver) {
        this.recordDoctorResolver = recordDoctorResolver;
    }

    public Long resolve(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof Long recordId) {
                return recordDoctorResolver.resolveDoctorId(recordId);
            }
            if (arg instanceof MedicalRecordRequestDto requestDto) {
                return requestDto.doctorId();
            }
        }
        throw new IllegalStateException(
                "DoctorId could not be resolved for method: "
                        + joinPoint.getSignature()
        );
    }
}
