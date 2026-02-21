package com.hospital.mediflow.Common.Aspects;

import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingFilterDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Common.Annotations.Access.Manager.*;
import com.hospital.mediflow.Common.Authorization.Model.BillingAccessData;
import com.hospital.mediflow.Common.Authorization.Model.DoctorAccessData;
import com.hospital.mediflow.Common.Authorization.Policy.Manager.ManagerPolicy;
import com.hospital.mediflow.Common.Providers.Abstracts.CurrentUserProvider;
import com.hospital.mediflow.Common.Resolvers.Billing.BillingAccessDataResolver;
import com.hospital.mediflow.Common.Resolvers.Doctor.DoctorAccessDataResolver;
import com.hospital.mediflow.Common.Resolvers.Doctor.DoctorIdResolver;
import com.hospital.mediflow.DoctorDepartments.DataServices.Abstracts.DoctorDepartmentDataService;
import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
public class ManagerAccessAspect extends BaseAspect {
    private final ManagerPolicy accessPolicy;
    private final DoctorIdResolver doctorIdResolver;
    private final DoctorAccessDataResolver doctorAccessDataResolver;
    private final BillingAccessDataResolver billingAccessDataResolver;
    private final CurrentUserProvider userProvider;

    @Before("@annotation(access) && args(patientId,..)")
    public void checkPatientAccess(ManagerPatientAccess access,Long patientId){
        accessPolicy.assertCanAccessPatient(userProvider.get(),patientId,access.type());
    }
    @Before("@annotation(access)")
    public void checkAccess(ManagerRecordAccess access,JoinPoint joinPoint) {
        Long doctorId = doctorIdResolver.resolve(joinPoint);
        accessPolicy.assertCanAccessMedicalRecord(userProvider.get(),doctorId,access.type());
    }

    @Before("@annotation(access)")
    public void checkDoctorAccess(JoinPoint jp, ManagerDoctorAccess access) {
        DoctorAccessData data =doctorAccessDataResolver.resolve(jp,access.type());
        accessPolicy.assertCanAccessDoctor(userProvider.get(),data,access.type());
    }
    @Before("@annotation(access)")
    public void checkBillingAccess(JoinPoint jp, ManagerBillingAccess access) {
        BillingAccessData data = billingAccessDataResolver.resolve(jp,access.type());
        accessPolicy.assertCanAccessBilling(userProvider.get(),data,access.type());
    }

    @Before("@annotation(access)")
    public void checkAppointmentAccess(JoinPoint jp, ManagerAppointmentAccess access){
        Long appointmentId = extract(jp,Long.class);
        accessPolicy.assertCanAccessAppointment(userProvider.get(),appointmentId,access.type());
    }
}
