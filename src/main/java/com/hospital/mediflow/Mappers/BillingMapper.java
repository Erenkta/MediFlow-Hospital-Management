package com.hospital.mediflow.Mappers;

import com.hospital.mediflow.Billing.Domain.Dtos.BillingRequestDto;
import com.hospital.mediflow.Billing.Domain.Dtos.BillingResponseDto;
import com.hospital.mediflow.Billing.Domain.Entity.Billing;
import org.mapstruct.*;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BillingMapper {
    @Mappings(
            {
                    @Mapping(target = "patientId",source = "patient.id")
            }
    )
    BillingResponseDto toDto(Billing entity);
    Billing toEntity(BillingRequestDto requestDto);
    void toEntity(@MappingTarget Billing entity, BillingRequestDto request);
}
