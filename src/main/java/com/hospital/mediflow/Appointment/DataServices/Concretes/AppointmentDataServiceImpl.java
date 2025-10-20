package com.hospital.mediflow.Appointment.DataServices.Concretes;

import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentFilterDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Repository.AppointmentRepository;
import com.hospital.mediflow.Common.BaseService;
import com.hospital.mediflow.Mappers.AppointmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AppointmentDataServiceImpl extends BaseService<Appointment,Long> implements AppointmentDataService {
    private final AppointmentMapper mapper;

    AppointmentDataServiceImpl(AppointmentRepository repository,AppointmentMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    @Override
    public List<AppointmentResponseDto> findAll(AppointmentFilterDto filterDto) {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<AppointmentResponseDto> findAll(Pageable pageable, AppointmentFilterDto filterDto) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public AppointmentResponseDto findById(Long id) {
        return mapper.toDto(this.findByIdOrThrow(id));
    }

    @Override
    public AppointmentResponseDto save(AppointmentRequestDto appointmentRequestDto) {
        return mapper.toDto(repository.save(mapper.toEntity(appointmentRequestDto)));
    }

    @Override
    public AppointmentResponseDto update(Long id, AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = this.findByIdOrThrow(id);
        mapper.updateEntity(appointment,appointmentRequestDto);
        repository.save(appointment);
        return mapper.toDto(appointment);
    }

    @Override
    public void deleteById(Long id) {
        isExistsOrThrow(id);
        repository.deleteById(id);
    }
}
