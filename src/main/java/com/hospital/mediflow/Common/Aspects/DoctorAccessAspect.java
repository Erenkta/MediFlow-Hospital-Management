package com.hospital.mediflow.Common.Aspects;

import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Common.Annotations.Access.Doctor.DoctorAppointmentAccess;
import com.hospital.mediflow.Common.Annotations.Access.Doctor.DoctorPatientAccess;
import com.hospital.mediflow.Common.Annotations.Access.Doctor.DoctorRecordAccess;
import com.hospital.mediflow.Common.Authorization.Policy.Doctor.DoctorPolicyDeprecated;
import com.hospital.mediflow.Common.Providers.Abstracts.CurrentUserProvider;
import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordFilterDto;
import com.hospital.mediflow.Security.UserDetails.MediflowUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class DoctorAccessAspect extends BaseAspect {
    private final DoctorPolicyDeprecated accessPolicy;
    private final CurrentUserProvider userProvider;

    @Before("@annotation(access) && args(patientId,..)")
    public void checkAccess(Long patientId, DoctorPatientAccess access){
         accessPolicy.assertCanAccessPatient(userProvider.get(),patientId,access.type());
    }

    @Before("@annotation(access) && args(recordId,..)")
    public void checkRecordAccess(DoctorRecordAccess access, Long recordId){
        accessPolicy.assertCanAccessMedicalRecord(userProvider.get(),recordId,access.type());
    }

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

    @Before("@annotation(access)")
    public void checkAppointmentAccess(JoinPoint jp, DoctorAppointmentAccess access){
        Long appointmentId = extract(jp,Long.class);
        accessPolicy.assertCanAccessAppointment(userProvider.get(),appointmentId,access.type());
    }
}
