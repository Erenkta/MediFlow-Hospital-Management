package com.hospital.mediflow.Common.Aspects;

import com.hospital.mediflow.Common.Providers.Abstracts.CurrentUserProvider;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class DoctorAccessAspect extends BaseAspect {
    private final CurrentUserProvider userProvider;

    @Around("@annotation(com.hospital.mediflow.Common.Annotations.Access.Doctor.AutoFillDoctorId)")
    public Object autoFillPatientFilter(ProceedingJoinPoint pjp) throws Throwable{
        Object[] args = pjp.getArgs();
        Object[] newArgs = args.clone();
        Long resourceId = MediflowUserDetailsService.currentUser().getResourceId();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof MedicalRecordFilterDto filter) {
                newArgs[i] = new MedicalRecordFilterDto(
                        resourceId,
                        filter.patientId(),
                        filter.doctorName(),
                        filter.patientName(),
                        filter.departmentName(),
                        filter.recordDateStart(),
                        filter.recordDateEnd()
                );
            }
        }
        return pjp.proceed(newArgs);
    }

}
