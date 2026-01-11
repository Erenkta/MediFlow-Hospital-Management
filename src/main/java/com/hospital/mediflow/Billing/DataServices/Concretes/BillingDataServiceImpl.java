package com.hospital.mediflow.Billing.DataServices.Concretes;

import com.hospital.mediflow.Billing.DataServices.Abstracts.BillingDataService;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingFilterDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import com.hospital.mediflow.Billing.Repositories.BillingRepository;
import com.hospital.mediflow.Common.BaseService;
import com.hospital.mediflow.Common.Helpers.Predicate.BillingPredicateBuilder;
import com.hospital.mediflow.Mappers.BillingMapper;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BillingDataServiceImpl extends BaseService<Billing,Long> implements BillingDataService {
    private final BillingMapper mapper;
    private final BillingRepository repository;
    private final BillingPredicateBuilder filterBuilder;
    public BillingDataServiceImpl(BillingRepository repository,BillingMapper mapper,BillingPredicateBuilder filterBuilder) {
        super(repository);
        this.repository = repository;
        this.mapper = mapper;
        this.filterBuilder = filterBuilder;
    }

    @Override
    public List<BillingResponseDto> findAllBillings(BillingFilterDto medicalRecordFilter) {
        Predicate filter = filterBuilder
                .withAmount(medicalRecordFilter.amountLessThan(),medicalRecordFilter.amountGreaterThan())
                .withBillingDate(medicalRecordFilter.billingDateStart(),medicalRecordFilter.billingDateEnd())
                .withPatientName(medicalRecordFilter.patientName())
                .withStatus(medicalRecordFilter.status())
                .build();
        return repository.findAll(filter).stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<BillingResponseDto> findAllBillings(Pageable pageable, BillingFilterDto medicalRecordFilter) {
        Predicate filter = filterBuilder
                .withAmount(medicalRecordFilter.amountLessThan(),medicalRecordFilter.amountGreaterThan())
                .withBillingDate(medicalRecordFilter.billingDateStart(),medicalRecordFilter.billingDateEnd())
                .withPatientName(medicalRecordFilter.patientName())
                .withStatus(medicalRecordFilter.status())
                .build();
        return repository.findAll(filter, pageable).map(mapper::toDto);
    }

    @Override
    public BillingResponseDto findBillingById(Long id) {
        return mapper.toDto(this.findByIdOrThrow(id));
    }

    @Override
    public BillingResponseDto createBilling(BillingRequestDto medicalRecord) {
        Billing entity = mapper.toEntity(medicalRecord);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public BillingResponseDto updateBilling(Long id, BillingRequestDto medicalRecord) {
        Billing entity = this.findByIdOrThrow(id);
        mapper.toEntity(entity, medicalRecord);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void deleteBilling(Long id) {
        this.isExistsOrThrow(id);
        repository.deleteById(id);
    }
}
