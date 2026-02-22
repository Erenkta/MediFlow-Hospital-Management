package com.hospital.mediflow.Mappers;

import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordRequestDto;
import com.hospital.mediflow.MedicalRecords.Domain.Dtos.MedicalRecordResponseDto;
import com.hospital.mediflow.MedicalRecords.Domain.Entity.MedicalRecord;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyRequestDto;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
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
