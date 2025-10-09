package com.hospital.mediflow.Doctor.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorFilterDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import com.hospital.mediflow.Doctor.Repositories.DoctorRepository;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DoctorControllerIntegrationTest {
    @Autowired
    private  MockMvc mockMvc;
    @Autowired
    private  DoctorRepository doctorRepository;
    @Autowired
    private  ObjectMapper mapper;

    private final String API_URI ="/api/v1/doctors";

    //Create Doctor Endpoint tests
    @Test
    void should_save_doctor() throws Exception {
        DoctorRequestDto requestDto = DoctorRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty("005")
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
    void should_save_doctor_throw_exception_when_request_body_is_invalid() throws Exception {
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
    //

    //Get Doctors Endpoint tests
    @Test
    void should_find_doctors_return_doctor_list() throws Exception {
        Specialty specialty = Specialty.builder().code("005").build();
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(specialty)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(specialty)
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
    void should_find_doctors_return_doctor_list_empty() throws Exception {
        mockMvc.perform(get(API_URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
    @Test
    void should_find_doctors_return_doctor_list_filtered() throws Exception {
        Specialty specialty = Specialty.builder().code("005").build();

        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(specialty)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(specialty)
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
    void should_find_doctors_return_doctor_list_empty_filtered() throws Exception {
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
    void should_find_doctors_return_doctor_page() throws Exception {
        Specialty specialty = Specialty.builder().code("005").build();

        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(specialty)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(specialty)
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
    void should_find_doctors_return_doctor_page_empty() throws Exception {
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
    void should_find_doctors_return_doctor_page_filtered() throws Exception {
        Specialty specialty = Specialty.builder().code("005").build();

        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(specialty)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(specialty)
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
    void should_find_doctors_return_doctor_page_empty_filtered() throws Exception {
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
    //

    //Get Doctors By Doctor Code Endpoint tests
    @Test
    void should_find_by_doctor_code_return_doctor_list_title_filtered() throws Exception{
        Specialty immunology = Specialty.builder().code("005").build();
        Specialty hematology = Specialty.builder().code("003").build();

        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(immunology)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(hematology)
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
    void should_find_by_doctor_code_throw_exception_when_title_filter_is_invalid() throws Exception{
        Specialty immunology = Specialty.builder().code("005").build();
        Specialty hematology = Specialty.builder().code("003").build();
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(immunology)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(hematology)
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
    void should_find_by_doctor_code_return_doctor_list_empty_title_filtered() throws Exception{
        mockMvc.perform(get(API_URI+"/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("title","ASSISTANT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }

    @Test
    void should_find_by_doctor_code_return_doctor_list_specialty_filtered() throws Exception{
        Specialty immunology = Specialty.builder().code("005").build();
        Specialty hematology = Specialty.builder().code("003").build();
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(immunology)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(hematology)
                .title(TitleEnum.ASSISTANT)
                .build();
        doctorRepository.save(first_doctor);
        doctorRepository.save(second_doctor);

        mockMvc.perform(get(API_URI+"/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("specialty","Hematology"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Jane"))
                .andExpect(jsonPath("length()").value(1));
    }

    @Test
    void should_find_by_doctor_code_throw_exception_when_specialty_filter_is_invalid() throws Exception{
        Specialty immunology = Specialty.builder().code("005").build();
        Specialty hematology = Specialty.builder().code("003").build();
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(immunology)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(hematology)
                .title(TitleEnum.ASSISTANT)
                .build();
        doctorRepository.save(first_doctor);
        doctorRepository.save(second_doctor);

        mockMvc.perform(get(API_URI+"/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("title","President"))
                .andExpect(status().is(Integer.parseInt(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getStatusCode())))
                .andExpect(jsonPath("$.errorCode").value("METHOD_ARGUMENT_NOT_VALID"))
                .andExpect(jsonPath("$.message").value("Argument validation has failed. Please check the parameter value : President"));
    }

    @Test
    void should_find_by_doctor_code_return_doctor_list_empty_specialty_filtered() throws Exception{
        mockMvc.perform(get(API_URI+"/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr("specialty","Invalid Specialty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }
    @Test
    void should_find_by_doctor_code_return_doctor_list_doctorCode_filtered() throws Exception{
        Specialty immunology = Specialty.builder().code("005").build();
        Specialty hematology = Specialty.builder().code("003").build();
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(immunology)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(hematology)
                .title(TitleEnum.ASSISTANT)
                .build();
        doctorRepository.save(first_doctor);
        doctorRepository.save(second_doctor);

        mockMvc.perform(get(API_URI+"/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("title","ASSISTANT")
                        .requestAttr("specialty","Hematology"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].doctorCode").value(201003L))
                .andExpect(jsonPath("length()").value(1));
    }

    @Test
    void should_find_by_doctor_code_throw_exception_when_doctorCode_filter_is_invalid() throws Exception{
        Specialty immunology = Specialty.builder().code("005").build();
        Specialty hematology = Specialty.builder().code("003").build();
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(immunology)
                .title(TitleEnum.INTERN)
                .build();
        Doctor second_doctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@doe.com")
                .phone("0559654512")
                .specialty(hematology)
                .title(TitleEnum.ASSISTANT)
                .build();
        doctorRepository.save(first_doctor);
        doctorRepository.save(second_doctor);

        mockMvc.perform(get(API_URI+"/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("title","President")
                        .queryParam("specialty","Hematology"))
                .andExpect(status().is(Integer.parseInt(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getStatusCode())))
                .andExpect(jsonPath("$.errorCode").value("METHOD_ARGUMENT_NOT_VALID"))
                .andExpect(jsonPath("$.message").value("Argument validation has failed. Please check the parameter value : President"));
    }

    @Test
    void should_return_doctor_list_empty_doctorCode_filtered() throws Exception{
        mockMvc.perform(get(API_URI+"/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("title","ASSISTANT")
                        .param("specialty","HEMATOLOGY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }
    //

    //Get Doctors By ID Endpoint tests
    @Test
    void should_find_doctor_by_id_return_doctor() throws Exception{
        Specialty immunology = Specialty.builder().code("005").build();
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(immunology)
                .title(TitleEnum.INTERN)
                .build();
        Doctor createdDoctor = doctorRepository.save(first_doctor);

        mockMvc.perform(get(API_URI+"/"+createdDoctor.getId()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.id").value(createdDoctor.getId()));
    }

    @Test
    void should_find_by_id_throw_exception_when_doctor_not_found() throws Exception{
        mockMvc.perform(get(API_URI+"/10000"))
                .andExpect(status().is(Integer.parseInt(ErrorCode.RECORD_NOT_FOUND.getStatusCode())))
                .andExpect(jsonPath("$.errorCode").value("RECORD_NOT_FOUND"));
    }
    //

    // Update Doctor Endpoint Tests
    @Test
    void should_update_doctor_successfully() throws Exception{
        Specialty immunology = Specialty.builder().code("005").name("Immunology").build();
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(immunology)
                .title(TitleEnum.INTERN)
                .build();
        Doctor savedDoctor = doctorRepository.save(first_doctor);

        DoctorRequestDto requestDto = DoctorRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty("004")
                .title(TitleEnum.PROFESSOR)
                .build();

        String content = mapper.writeValueAsString(requestDto);

        mockMvc.perform(put(API_URI+"/"+savedDoctor.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.specialty").value("Neurology"))
                .andExpect(jsonPath("$.title").value("Professor"));
    }

    @Test
    void should_update_doctor_throw_exception_when_id_is_invalid() throws Exception{
        Doctor invalid_doctor = Doctor.builder()
                .firstName("John Updated")
                .lastName("Doe new")
                .email("john@doe.com")
                .phone("0555324512")
                .build();

        String content = mapper.writeValueAsString(invalid_doctor);

        mockMvc.perform(put(API_URI+"/100244")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(Integer.parseInt(ErrorCode.RECORD_NOT_FOUND.getStatusCode())))
                .andExpect(jsonPath("$.errorCode").value("RECORD_NOT_FOUND"));
    }
    //

    // Delete Doctor Endpoint Tests
    @Test
    void should_delete_doctor_successfully() throws Exception{
        Specialty immunology = Specialty.builder().code("005").build();
        Doctor first_doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(immunology)
                .title(TitleEnum.INTERN)
                .build();
        Doctor savedDoctor = doctorRepository.save(first_doctor);
        savedDoctor.setTitle(TitleEnum.PROFESSOR);


        mockMvc.perform(delete(API_URI+"/"+savedDoctor.getId()))
                .andExpect(status().is(204));


    }

    @Test
    void should_delete_doctor_throw_exception_when_id_is_invalid() throws Exception{
        mockMvc.perform(delete(API_URI+"/100244"))
                .andExpect(status().is(Integer.parseInt(ErrorCode.RECORD_NOT_FOUND.getStatusCode())))
                .andExpect(jsonPath("$.errorCode").value("RECORD_NOT_FOUND"));
    }
    //

}
