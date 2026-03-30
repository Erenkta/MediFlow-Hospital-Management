package com.hospital.mediflow.Billing.Services.Concretes;

import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Enums.BillingStatus;
import com.hospital.mediflow.Billing.Enums.BillingType;
import com.hospital.mediflow.Billing.Services.Abstracts.BillingService;
import com.hospital.mediflow.Common.Configuration.Properties.BillingProperties;
import com.hospital.mediflow.Common.Events.EventType;
import com.hospital.mediflow.Common.Events.InternalNotificationEvent;
import com.hospital.mediflow.Common.Exceptions.AppointmentNotExistsException;
import com.hospital.mediflow.Common.Exceptions.RecordNotFoundException;
import com.hospital.mediflow.Security.Dtos.Entity.User;
import com.hospital.mediflow.Security.UserDetails.Repository.UserRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillingServiceImpl implements BillingService {
    private final BillingDataService dataService;
    private final AppointmentDataService appointmentService;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    @PreAuthorize("hasAuthority('patient:read')")
    public List<BillingResponseDto> findAllBillings(Predicate billingFilterDto) {
        return dataService.findAllBillings(billingFilterDto);
    }

    @Override
    @PreAuthorize("hasAuthority('patient:read')")
    public Page<BillingResponseDto> findAllBillings(Pageable pageable, Predicate billingFilterDto) {
        return dataService.findAllBillings(pageable, billingFilterDto);
    }

    @Override
    @PreAuthorize("hasAuthority('patient:read')")
    public BillingResponseDto findBillingById(Long id) {
        return dataService.findBillingById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:create')")
    public BillingResponseDto createBilling(BillingRequestDto billingRequest) {
        return dataService.createBilling(billingRequest);
    }

    @Override
    public BillingResponseDto createBilling(Appointment appointment,BillingType billingType, double amount) {
        
        Long patientId = appointment.getPatient().getId();
        boolean isAppointmentExists = appointmentService.isAppointmentPatientRelationExists(appointment.getId(),patientId);
        if(!isAppointmentExists){
            String message = String.format("Appoinment with id : '%s' couldn't be find for the patient with id '%s'",appointment.getId(),patientId);
            throw new AppointmentNotExistsException(message);
        }

        BillingRequestDto requestDto = new BillingRequestDto(
                patientId,
                appointment.getDoctor().getDoctorDepartment().stream().findFirst().get().getId().getDepartmentId(),
                appointment.getId(),
                BigDecimal.valueOf(amount),
                BillingStatus.PENDING,
                billingType,
                appointment.getAppointmentDate().minusMinutes(30),
                LocalDateTime.now()
        );
        return dataService.createBilling(requestDto);
    }

    @Override
    public BillingResponseDto cancelBilling(Long appointmentId) {
        BillingResponseDto response = dataService.findBillingByAppointmentAndType(appointmentId,BillingType.DEPOSIT,AppointmentStatusEnum.PENDING).orElseThrow(()->{
            String message = String.format("Billing with appointment id '%s' cannot found",appointmentId);
            return new RecordNotFoundException(message);
        });
        return dataService.updateBillingStatus(response.id(),BillingStatus.CANCELLED);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('manager:update')")
    public BillingResponseDto updateBilling(Long id, BillingRequestDto billingRequest) {
        return dataService.updateBilling(id, billingRequest);
    }

    @Override
    public void notifyPatient(Long appointmentId, EventType type, Long userId, Map<String, String> notifyParams) {
        BillingResponseDto billing = dataService.findBillingByAppointmentAndType(
                appointmentId,
                BillingType.valueOf(notifyParams.get("billingType")),
                AppointmentStatusEnum.valueOf(notifyParams.get("appointmentStatus"))
                ).orElseThrow(()->new RecordNotFoundException("Billing with appointment id '"+appointmentId+"' couldn't be found"));
        Map<String,String> defaultParams = Map.of(
                "billingDate",billing.billingDate().toString(),
                "paymentDate",billing.paymentDate().toString(),
                "amount",billing.amount().toString());

        Map<String, String> combined = Stream.concat(notifyParams.entrySet().stream(), defaultParams.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (value1, value2) -> value1 // Çakışma durumunda ilkini seç (V1)
                ));
        User user = userRepository.findByResourceId(userId);

        eventPublisher.publishEvent(new InternalNotificationEvent(
                billing,
                user,
                type,
                combined
        ));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('manager:delete')")
    public void deleteBilling(Long id) {
        dataService.deleteBilling(id);
    }
}
