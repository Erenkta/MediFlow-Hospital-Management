package com.hospital.mediflow.Appointment.Services.Concretes;

import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentFilterDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import com.hospital.mediflow.Appointment.Services.Abstracts.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentDataService appointmentDataService;
    @Override
    public List<AppointmentResponseDto> findAll(AppointmentFilterDto filterDto) {
        return appointmentDataService.findAll(filterDto);
    }

    @Override
    public Page<AppointmentResponseDto> findAll(Pageable pageable, AppointmentFilterDto filterDto) {
        return appointmentDataService.findAll(pageable, filterDto);
    }

    @Override
    public AppointmentResponseDto findById(Long id) {
        return appointmentDataService.findById(id);
    }

    @Override
    @Transactional
    public AppointmentResponseDto save(AppointmentRequestDto appointmentRequestDto) {
        return appointmentDataService.save(appointmentRequestDto);
    }

    @Override
    @Transactional
    public AppointmentResponseDto update(Long id, AppointmentRequestDto appointmentRequestDto) {
        return appointmentDataService.update(id, appointmentRequestDto);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        appointmentDataService.deleteById(id);
    }
}
