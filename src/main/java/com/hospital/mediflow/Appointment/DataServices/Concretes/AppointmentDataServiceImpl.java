package com.hospital.mediflow.Appointment.DataServices.Concretes;

import com.hospital.mediflow.Appointment.DataServices.Abstracts.AppointmentDataService;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentFilterDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentRequestDto;
import com.hospital.mediflow.Appointment.Domain.Dtos.AppointmentResponseDto;
import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.Appointment.Enums.AppointmentStatusEnum;
import com.hospital.mediflow.Appointment.Enums.States.AppointmentState;
import com.hospital.mediflow.Appointment.Repository.AppointmentRepository;
import com.hospital.mediflow.Common.BaseService;
import com.hospital.mediflow.Common.Configuration.AppointmentProperties;
import com.hospital.mediflow.Common.Exceptions.AppointmentNotAvailableException;
import com.hospital.mediflow.Common.Specifications.AppointmentSpecification;
import com.hospital.mediflow.Doctor.DataServices.Abstracts.DoctorDataService;
import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.hospital.mediflow.Mappers.AppointmentMapper;
import com.hospital.mediflow.Patient.DataServices.Abstracts.PatientDataService;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.stream.Streams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class AppointmentDataServiceImpl extends BaseService<Appointment,Long> implements AppointmentDataService {
    private final AppointmentMapper mapper;
    private final AppointmentRepository extendedRepository;
    private final PatientDataService patientDataService;
    private final DoctorDataService doctorDataService;

    AppointmentDataServiceImpl(AppointmentRepository repository,
                               AppointmentMapper mapper,
                               PatientDataService patientDataService,
                               DoctorDataService doctorDataService) {
        super(repository);
        this.extendedRepository = repository;
        this.patientDataService = patientDataService;
        this.doctorDataService = doctorDataService;
        this.mapper = mapper;
    }

    @Override
    public List<AppointmentResponseDto> findAll(AppointmentFilterDto filterDto) {
        return extendedRepository.findAll(AppointmentSpecification.filter(filterDto)).stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<AppointmentResponseDto> findAll(Pageable pageable, AppointmentFilterDto filterDto) {
        return extendedRepository.findAll(AppointmentSpecification.filter(filterDto),pageable).map(mapper::toDto);
    }

    @Override
    public AppointmentResponseDto findById(Long id) {
        return mapper.toDto(this.findByIdOrThrow(id));
    }

    @Override
    public AppointmentResponseDto save(AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = mapper.toEntity(appointmentRequestDto);
        Patient patient = patientDataService.getReferenceById(appointmentRequestDto.patientId());
        Doctor doctor = doctorDataService.getReferenceById(appointmentRequestDto.doctorId());

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        return mapper.toDto(repository.save(appointment));
    }
    @Override
    public boolean isAppointmentAvailable(Long doctorId,LocalDateTime appointmentDate){
        return extendedRepository.findAll(
                AppointmentSpecification.filterAvailableAppointments(
                        doctorId,
                        appointmentDate.plusMinutes(30),
                        appointmentDate.minusMinutes(30)
                )).isEmpty();
    }

    @Override
    public AppointmentResponseDto update(Long id, Appointment appointment) {
        repository.save(appointment);
        return mapper.toDto(appointment);
    }
    @Override
    public Appointment getReferenceById(Long id){
        return this.findByIdOrThrow(id);
    }

    @Override
    public void deleteById(Long id) {
        Appointment appointment = this.findByIdOrThrow(id);
        appointment.setStatus(AppointmentStatusEnum.REJECTED);
        repository.save(appointment);
    }

    @Override
    public List<LocalTime> getAvailableAppointmentDates(Long doctorId, LocalDateTime startDateTime,LocalDateTime endDateTime){
        List<LocalTime> appointmentDates = new ArrayList<>();
        LocalDateTime cursor = startDateTime;

        while (!cursor.isAfter(endDateTime)) {
            appointmentDates.add(cursor.toLocalTime());
            cursor = cursor.plusMinutes(30);
        }
        Set<LocalTime> occupiedDates = new HashSet<>(extendedRepository.findDoctorAppointmentDates(startDateTime, endDateTime, doctorId)
                .stream()
                .map(Timestamp::toLocalDateTime)
                .map(item -> LocalTime.of(item.getHour(),item.getMinute(),item.getSecond()))
                .toList());
       return appointmentDates.stream().filter(item -> !occupiedDates.contains(item)).sorted(Comparator.naturalOrder()).toList();
    }
}
