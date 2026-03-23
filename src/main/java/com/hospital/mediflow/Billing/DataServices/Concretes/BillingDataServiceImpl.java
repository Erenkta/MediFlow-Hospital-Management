package com.hospital.mediflow.Billing.DataServices.Concretes;

import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Audit.Event.DomainEvent;
import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Repositories.BillingRepository;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Audit.Audit;
import com.hospital.mediflow.Common.BaseService;
import com.hospital.mediflow.Department.DataServices.Abstracts.DepartmentDataService;
import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Mappers.BillingMapper;
import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BillingDataServiceImpl extends BaseService<Billing,Long> implements BillingDataService {
    private final BillingMapper mapper;
    private final BillingRepository repository;
    private final AppointmentDataService appointmentDataService;
    private final DepartmentDataService departmentDataService;
    private final PatientDataService patientDataService;
    private final ApplicationEventPublisher publisher;

    public BillingDataServiceImpl(BillingRepository repository, BillingMapper mapper, AppointmentDataService appointmentDataService, DepartmentDataService departmentDataService, PatientDataService patientDataService, ApplicationEventPublisher publisher) {
        super(repository);
        this.repository = repository;
        this.mapper = mapper;
        this.appointmentDataService = appointmentDataService;
        this.departmentDataService = departmentDataService;
        this.patientDataService = patientDataService;
        this.publisher = publisher;
    }

    @Override
    public List<BillingResponseDto> findAllBillings(Predicate medicalRecordFilter) {
        return repository.findAll(medicalRecordFilter).stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<BillingResponseDto> findAllBillings(Pageable pageable, Predicate medicalRecordFilter) {
        return repository.findAll(medicalRecordFilter, pageable).map(mapper::toDto);
    }

    @Override
    public BillingResponseDto findBillingById(Long id) {
        return mapper.toDto(this.findByIdOrThrow(id));
    }

    @Override
    public BillingResponseDto createBilling(BillingRequestDto billingRequestDto) {
        Billing entity = mapper.toEntity(billingRequestDto);
        Appointment appointment = appointmentDataService.getReferenceById(billingRequestDto.appointmentId());
        Department department = departmentDataService.getReferenceById(billingRequestDto.departmentId());
        Patient patient = patientDataService.getReferenceById(billingRequestDto.patientId());

        entity.setPatient(patient);
        entity.setAppointment(appointment);
        entity.setDepartment(department);

        return mapper.toDto(repository.save(entity));
    }

    @Override
    public BillingResponseDto updateBilling(Long id, BillingRequestDto medicalRecord) {
        Billing entity = this.findByIdOrThrow(id);
        mapper.toEntity(entity, medicalRecord);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public boolean isBillingDepartmentRelationExists(Long billingId, Long departmentId) {
        return repository.isBillingDepartmentRelationExists(billingId, departmentId);
    }

    @Override
    public boolean isBillingPatientRelationExists(Long billingId, Long patientId) {
        return repository.isBillingPatientRelationExists(billingId, patientId);
    }

    @Override
    @Audit(action = AccessType.DELETE,returns = Billing.class)
    public void deleteBilling(Long id) {
        this.isExistsOrThrow(id);
        Billing deletedEntity = repository.findById(id).get();
        publisher.publishEvent(new DomainEvent<BillingResponseDto>(mapper.toDto(deletedEntity),AccessType.DELETE,id, 90));

        repository.deleteById(id);
    }
}
