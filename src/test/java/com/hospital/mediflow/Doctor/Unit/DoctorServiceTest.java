package com.hospital.mediflow.Doctor.Unit;

import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Common.Exceptions.RecordNotFoundException;
import com.hospital.mediflow.Doctor.DataServices.Abstracts.DoctorDataService;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import com.hospital.mediflow.Doctor.Services.Concretes.DoctorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;



@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
    @Mock
    private DoctorDataService dataService;

    @InjectMocks
    private DoctorServiceImpl service;

    @Test
    void test_saveDoctor_is_success(){
        DoctorResponseDto doctor = DoctorResponseDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty("005")
                .title(TitleEnum.INTERN)
                .doctorCode(101005L)
                .build();

        DoctorRequestDto requestDto = DoctorRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty("005")
                .title(TitleEnum.INTERN)
                .build();

        Mockito.when(dataService.save(Mockito.any(DoctorRequestDto.class))).thenReturn(doctor);

        DoctorResponseDto response = service.saveDoctor(requestDto);
        Assertions.assertEquals("John",response.firstName());
        Assertions.assertEquals(101005L,response.doctorCode());
    }
    @Test
    void test_saveDoctor_is_failure_when_request_body_is_incorrect(){
        DoctorRequestDto requestDto = DoctorRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty("005")
                .build();

        Mockito.when(dataService.save(Mockito.any(DoctorRequestDto.class)))
                .thenThrow(new IllegalArgumentException("Title cannot be null"));

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                ()->service.saveDoctor(requestDto)
        );
        Assertions.assertEquals("Title cannot be null",exception.getMessage());
    }

    @Test
    void test_updateDoctor_is_success(){
        DoctorResponseDto updateResponse = DoctorResponseDto.builder()
                .firstName("John Updated")
                .lastName("Doe Updated")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty("Cardiology")
                .title(TitleEnum.INTERN)
                .build();

        DoctorRequestDto updateRequestDto = DoctorRequestDto.builder()
                .firstName("John Updated")
                .lastName("Doe Updated")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty("001")
                .title(TitleEnum.INTERN)
                .build();

        Mockito.when(dataService.update(
                eq(10000L),
                Mockito.any(DoctorRequestDto.class)
        )).thenReturn(updateResponse);
        DoctorResponseDto response = service.updateDoctor(10000L,updateRequestDto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("John Updated", response.firstName());
        Assertions.assertEquals("Cardiology", response.specialty());
    }

    @Test
    void test_updateDoctor_is_failure_when_given_id_is_invalid(){

        DoctorRequestDto updateRequestDto = DoctorRequestDto.builder()
                .firstName("John Updated")
                .lastName("Doe Updated")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty("001")
                .title(TitleEnum.INTERN)
                .build();

        Mockito.when(dataService.update(
                any(),
                Mockito.any(DoctorRequestDto.class)
        )).thenThrow(new RecordNotFoundException("Doctor couldn't be found. Please try again with different ID"));


        RecordNotFoundException exception = Assertions.assertThrows(
                RecordNotFoundException.class,
                ()->service.updateDoctor(10000L,updateRequestDto)
        );
        Assertions.assertEquals("Doctor couldn't be found. Please try again with different ID",exception.getMessage());
        Assertions.assertEquals(ErrorCode.RECORD_NOT_FOUND,exception.getErrorCode());
    }

    @Test
    void test_findById_is_success(){
        DoctorResponseDto responseDto = DoctorResponseDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty("001")
                .title(TitleEnum.INTERN)
                .build();

        Mockito.when(dataService.findById(any())).thenReturn(responseDto);

        DoctorResponseDto response = service.findDoctorById(1000L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("John",response.firstName());
    }

    @Test
    void test_findById_is_failure_when_doctor_with_given_id_not_found(){
        Mockito.when(dataService.findById(any())).thenThrow(new RecordNotFoundException("Doctor couldn't be found. Please try again with different ID"));

        RecordNotFoundException exception = Assertions.assertThrows(
                RecordNotFoundException.class,
                ()->service.findDoctorById(10000L)
        );
        Assertions.assertEquals("Doctor couldn't be found. Please try again with different ID",exception.getMessage());
        Assertions.assertEquals(ErrorCode.RECORD_NOT_FOUND,exception.getErrorCode());
    }

    @Test
    void test_findDoctorsByDoctorCode_is_success(){
        DoctorResponseDto responseDto = DoctorResponseDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty("Cardiology")
                .title(TitleEnum.INTERN)
                .build();
        Mockito.when(dataService.findByDoctorCode(any(String.class),any(TitleEnum.class))).thenReturn(List.of(responseDto));

        List<DoctorResponseDto> response = service.findDoctorsByDoctorCode("Cardiology",TitleEnum.INTERN);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals("Cardiology",response.getFirst().specialty());
        Assertions.assertEquals(TitleEnum.INTERN,response.getFirst().title());
    }
    @Test
    void test_findAll_is_success(){
        DoctorResponseDto responseDto = DoctorResponseDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty("001")
                .title(TitleEnum.INTERN)
                .build();
        DoctorFilterDto filterDto = DoctorFilterDto.builder()
                .firstName("John")
                .lastName("Doe")
                .specialties(List.of("001"))
                .title(TitleEnum.INTERN)
                .build();
        Mockito.when(dataService.findAll(filterDto)).thenReturn(List.of(responseDto));

        List<DoctorResponseDto> response = service.findDoctors(filterDto);
        Assertions.assertFalse(response.isEmpty());

    }
}
