package com.hospital.mediflow.Mappers;

import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import com.hospital.mediflow.MedicalRecords.Domain.Entity.MedicalRecord;
import org.mapstruct.*;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MedicalRecordMapper {
    @Mappings(
            {
                    @Mapping(source = "patient.fullName",target = "patientName"),
                    @Mapping(source = "doctor.fullName",target = "doctorName")
            }
    )
    MedicalRecordResponseDto toDto(MedicalRecord entity);
    MedicalRecord toEntity(MedicalRecordRequestDto requestDto);


    void toEntity(@MappingTarget MedicalRecord entity, MedicalRecordRequestDto request);
}
