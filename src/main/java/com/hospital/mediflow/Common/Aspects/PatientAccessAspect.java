package com.hospital.mediflow.Common.Aspects;

import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Common.Annotations.Access.Patient.PatientAppointmentAccess;
import com.hospital.mediflow.Common.Annotations.Access.Patient.PatientBillingAccess;
import com.hospital.mediflow.Common.Annotations.Access.Patient.PatientRecordAccess;
import com.hospital.mediflow.Common.Authorization.Model.BillingAccessData;
import com.hospital.mediflow.Common.Authorization.Policy.Patient.PatientPolicy;
import com.hospital.mediflow.Common.Providers.Abstracts.CurrentUserProvider;
import com.hospital.mediflow.Common.Resolvers.Billing.BillingAccessDataResolver;
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
public class PatientAccessAspect extends BaseAspect {
    private final PatientPolicy accessPolicy;
    private final BillingAccessDataResolver billingAccessDataResolver;
    private final CurrentUserProvider userProvider;
    private final AppointmentDataService appointmentDataService;

    @Around("@annotation(com.hospital.mediflow.Common.Annotations.Access.Patient.AutoFillPatientId)")
    public Object autoFillPatientFilter(ProceedingJoinPoint pjp) throws Throwable{
        Object[] args = pjp.getArgs();
        Object[] newArgs = args.clone();
        Long resourceId = MediflowUserDetailsService.currentUser().getResourceId();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof MedicalRecordFilterDto filter) {
                newArgs[i] = new MedicalRecordFilterDto(
                        filter.doctorId(),
                        resourceId,
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
    @Before("@annotation(access) && args(recordId,..)")
    public void checkRecordAccess(PatientRecordAccess access, Long recordId){
        accessPolicy.assertCanAccessMedicalRecord(userProvider.get(),recordId,access.type());
    }
    @Before("@annotation(access)")
    public void checkBillingAccess(JoinPoint jp,PatientBillingAccess access){
        BillingAccessData data = billingAccessDataResolver.resolve(jp,access.type());
        accessPolicy.assertCanAccessBilling(userProvider.get(),data,access.type());
    }

    @Before("@annotation(access)")
    public void checkAppointmentAccess(JoinPoint jp, PatientAppointmentAccess access){
        Long appointmentId = extract(jp,Long.class);
        accessPolicy.assertCanAccessAppointment(userProvider.get(),appointmentId,access.type());
    }
}
