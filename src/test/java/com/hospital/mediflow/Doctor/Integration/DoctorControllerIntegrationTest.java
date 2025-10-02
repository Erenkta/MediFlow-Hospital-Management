package com.hospital.mediflow.Doctor.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.hospital.mediflow.Doctor.Enums.SpecialtyEnum;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import com.hospital.mediflow.Doctor.Repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerIntegrationTest {
    @Autowired
    private  MockMvc mockMvc;
    @Autowired
    private  DoctorRepository doctorRepository;
    @Autowired
    private  ObjectMapper mapper;

    private final String API_URI ="/api/v1/doctors";



    @BeforeEach
    void setup(){
        doctorRepository.deleteAll();
    }

    @Test
    void should_save_doctor() throws Exception {
        DoctorRequestDto requestDto = DoctorRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(SpecialtyEnum.IMMUNOLOGY)
                .title(TitleEnum.INTERN)
                .build();

        String content = mapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/api/v1/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.doctorCode").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.specialty").value("Immunology"));
    }

    @Test
    void should_throw_exception_when_request_body_is_invalid() throws Exception {
        DoctorRequestDto requestDto = DoctorRequestDto.builder() // specialty is missing
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .title(TitleEnum.INTERN)
                .build();

        String content = mapper.writeValueAsString(requestDto);

        mockMvc.perform(post(API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(Integer.parseInt(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getStatusCode())))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.METHOD_ARGUMENT_NOT_VALID.toString()))
                .andExpect(jsonPath("$.message").value("Argument validation has failed."))
                .andExpect(jsonPath("$.fieldErrorList").exists());

    }

    @Test
    void should_return_doctor_list() throws Exception {
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(SpecialtyEnum.IMMUNOLOGY)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(SpecialtyEnum.HEMATOLOGY)
                .title(TitleEnum.ASSISTANT)
                .build();

        doctorRepository.save(first_doctor);
        doctorRepository.save(second_doctor);

        mockMvc.perform(get(API_URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    void should_return_doctor_list_empty() throws Exception {
        mockMvc.perform(get(API_URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
    @Test
    void should_return_doctor_list_filtered() throws Exception {
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(SpecialtyEnum.IMMUNOLOGY)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(SpecialtyEnum.HEMATOLOGY)
                .title(TitleEnum.ASSISTANT)
                .build();

        DoctorFilterDto doctorFilterDto = DoctorFilterDto.builder()
                        .firstName("Jane")
                        .build();

        doctorRepository.save(first_doctor);
        doctorRepository.save(second_doctor);

        mockMvc.perform(get(API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("firstName",doctorFilterDto.firstName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstName").value("Jane"))
                .andExpect(jsonPath("$.length()").value(1));
    }
    @Test
    void should_return_doctor_list_empty_filtered() throws Exception {
        DoctorFilterDto doctorFilterDto = DoctorFilterDto.builder()
                .firstName("Jane")
                .build();
        mockMvc.perform(get(API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("firstName",doctorFilterDto.firstName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
    @Test
    void should_return_doctor_page() throws Exception {
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(SpecialtyEnum.IMMUNOLOGY)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(SpecialtyEnum.HEMATOLOGY)
                .title(TitleEnum.ASSISTANT)
                .build();

        doctorRepository.save(first_doctor);
        doctorRepository.save(second_doctor);

        mockMvc.perform(get(API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page","0")
                        .param("size","2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable").exists())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.pageable.pageSize").exists());
    }

    @Test
    void should_return_doctor_page_empty() throws Exception {
        mockMvc.perform(get(API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page","0")
                        .param("size","2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable").exists())
                .andExpect(jsonPath("$.content.length()").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").exists());
    }
    @Test
    void should_return_doctor_page_filtered() throws Exception {
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(SpecialtyEnum.IMMUNOLOGY)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(SpecialtyEnum.HEMATOLOGY)
                .title(TitleEnum.ASSISTANT)
                .build();

        DoctorFilterDto doctorFilterDto = DoctorFilterDto.builder()
                .firstName("Jane")
                .build();

        doctorRepository.save(first_doctor);
        doctorRepository.save(second_doctor);

        mockMvc.perform(get(API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page","0")
                        .param("size","2")
                        .queryParam("firstName",doctorFilterDto.firstName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable").exists())
                .andExpect(jsonPath("$.content[0].firstName").value("Jane"))
                .andExpect(jsonPath("content.length()").value(1));
    }
    @Test
    void should_return_doctor_page_empty_filtered() throws Exception {
        DoctorFilterDto doctorFilterDto = DoctorFilterDto.builder()
                .firstName("Jane")
                .build();
        mockMvc.perform(get(API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page","0")
                        .param("size","2")
                        .queryParam("firstName",doctorFilterDto.firstName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable").exists())
                .andExpect(jsonPath("content.length()").value(0));
    }
    @Test
    void should_return_doctor_list_title_filtered() throws Exception{
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(SpecialtyEnum.IMMUNOLOGY)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(SpecialtyEnum.HEMATOLOGY)
                .title(TitleEnum.ASSISTANT)
                .build();
        doctorRepository.save(first_doctor);
        doctorRepository.save(second_doctor);

        mockMvc.perform(get(API_URI+"/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("title","ASSISTANT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Jane"))
                .andExpect(jsonPath("length()").value(1));
    }

    @Test
    void should_throw_exception_when_title_filter_is_invalid() throws Exception{
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(SpecialtyEnum.IMMUNOLOGY)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(SpecialtyEnum.HEMATOLOGY)
                .title(TitleEnum.ASSISTANT)
                .build();
        doctorRepository.save(first_doctor);
        doctorRepository.save(second_doctor);

        mockMvc.perform(get(API_URI+"/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("title","Assistant"))
                .andExpect(status().is(Integer.parseInt(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getStatusCode())))
                .andExpect(jsonPath("$.errorCode").value("METHOD_ARGUMENT_NOT_VALID"))
                .andExpect(jsonPath("$.message").value("Argument validation has failed. Please check the parameter value : Assistant"));
    }

    @Test
    void should_return_doctor_list_empty_title_filtered() throws Exception{
        mockMvc.perform(get(API_URI+"/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("title","ASSISTANT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }
}
