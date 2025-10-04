package com.hospital.mediflow.Specialty.DataServices.Concretes;

import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Common.Exceptions.RecordNotFoundException;
import com.hospital.mediflow.Mappers.SpecialtyMapper;
import com.hospital.mediflow.Specialty.DataServices.Abstracts.SpecialtyDataService;
import com.hospital.mediflow.Specialty.Domain.Dtos.SpecialtyResponseDto;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import com.hospital.mediflow.Specialty.Repositories.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecialtyDataServiceImpl implements SpecialtyDataService {
    private final SpecialtyRepository repository;
    private final SpecialtyMapper mapper;

    @Override
    public SpecialtyResponseDto findSpecialtyByCode(String code){
        Specialty specialty = repository.findByCode(code)
                .orElseThrow(() -> new RecordNotFoundException(
                        String.format("Specialty with code %s couldn't be found. Please try again with different code", code),
                        ErrorCode.RECORD_NOT_FOUND
                ));
        return mapper.toDto(specialty);
    }
}
