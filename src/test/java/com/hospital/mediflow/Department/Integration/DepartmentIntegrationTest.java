package com.hospital.mediflow.Department.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentFilterDto;
import com.hospital.mediflow.Department.Domain.Dtos.DepartmentRequestDto;
import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Department.Repository.DepartmentRepository;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import com.hospital.mediflow.Specialty.Repositories.SpecialtyRepository;
import com.mysema.commons.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class DepartmentIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DepartmentRepository repository;
    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Autowired
    private ObjectMapper mapper;


    private final String API_URI ="/api/v1/departments";

    @BeforeEach
    public void insertSpecialties(){
        Specialty specialty1 = Specialty.builder().name("Cardiology").code("001").build();
        Specialty specialty2 = Specialty.builder().name("N/A").code("000").build();

        specialtyRepository.saveAll(List.of(specialty1, specialty2));
    }

    //<editor-fold desc="Create Department Tests">
    @Test
    void should_save_department() throws Exception {
        DepartmentRequestDto requestDto = DepartmentRequestDto.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .build();

        String content = mapper.writeValueAsString(requestDto);

        mockMvc.perform(post(API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Department"))
                .andExpect(jsonPath("$.description").value("Mockito Test Department"));
    }
    @Test
    void should_save_department_when_specialties_are_empty() throws Exception {
        DepartmentRequestDto requestDto = DepartmentRequestDto.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .build();

        String content = mapper.writeValueAsString(requestDto);

        mockMvc.perform(post(API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Department"))
                .andExpect(jsonPath("$.description").value("Mockito Test Department"));
    }

    @Test
    void should_create_department_throw_exception_when_department_name_duplicated() throws Exception {
        Department request = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test").code("999").build()))
                .build();

        DepartmentRequestDto duplicatedNameDto = DepartmentRequestDto.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .build();

        repository.save(request);

        String content = mapper.writeValueAsString(duplicatedNameDto);

        mockMvc.perform(post(API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(Integer.parseInt(ErrorCode.RECORD_ALREADY_EXISTS.getStatusCode())))
                .andExpect(jsonPath("$.message").value("Department with name Test Department already exists"))
                .andExpect(jsonPath("$.errorCode").value("RECORD_ALREADY_EXISTS"));
    }
    //</editor-fold>

    //  <editor-fold desc="Get Department Tests ( List and Page )">
    @Test
    void should_get_departments_return_all_departments_list() throws Exception {
        Department requestNo1 = Department.builder()
                .name("Test Department NO1")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO1").code("999").build()))
                .build();

        Department requestNo2 = Department.builder()
                .name("Test Department NO2")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO2").code("998").build()))
                .build();

        repository.saveAll(List.of(requestNo1, requestNo2));

        mockMvc.perform(get(API_URI))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("length()").value(2));
    }

    @Test
    void should_get_departments_return_all_departments_list_name_filtered() throws Exception {
        Department requestNo1 = Department.builder()
                .name("Test Department NO1")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO1").code("999").build()))
                .build();

        Department requestNo2 = Department.builder()
                .name("Test Department NO2")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO2").code("998").build()))
                .build();

        DepartmentFilterDto filter = DepartmentFilterDto.builder().name("Test Department NO1").build();

        repository.saveAll(List.of(requestNo1, requestNo2));

        mockMvc.perform(get(API_URI)
                        .queryParam("name", filter.name()))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$[0].specialties[0].name").value("Test NO1"))
                .andExpect(jsonPath("length()").value(1));
    }
    @Test
    void should_get_departments_return_all_departments_list_description_filtered() throws Exception {
        Department requestNo1 = Department.builder()
                .name("Test Department NO1")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO1").code("999").build()))
                .build();

        Department requestNo2 = Department.builder()
                .name("Test Department NO2")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO2").code("998").build()))
                .build();

        repository.saveAll(List.of(requestNo1, requestNo2));

        mockMvc.perform(get(API_URI)
                        .queryParam("description", "Test"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("length()").value(2));
    }
    @Test
    void should_get_departments_return_all_departments_list_specialty_filtered() throws Exception {
        Specialty specialty1 = Specialty.builder().name("Test NO1").code("999").build();
        Specialty specialty2 = Specialty.builder().name("Test NO2").code("998").build();

        specialtyRepository.saveAll(List.of(specialty1,specialty2));
        Department requestNo1 = Department.builder()
                .name("Test Department NO1")
                .description("Mockito Test Department")
                .specialties(List.of(specialty1))
                .build();

        Department requestNo2 = Department.builder()
                .name("Test Department NO2")
                .description("Mockito Test Department")
                .specialties(List.of(specialty2))
                .build();

        requestNo1 = repository.save(requestNo1);
        requestNo2 = repository.save(requestNo2);
        specialty1.setDepartment(requestNo1);
        specialty2.setDepartment(requestNo2);
        specialtyRepository.saveAll(List.of(specialty1,specialty2));

        mockMvc.perform(get(API_URI)
                        .queryParam("specialties","999"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$[0].specialties[0].name").value("Test NO1"))
                .andExpect(jsonPath("length()").value(1));
    }
    @Test
    void should_get_departments_return_empty_list_when_filter_is_not_match() throws Exception {
        Department requestNo1 = Department.builder()
                .name("Test Department NO1")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO1").code("999").build()))
                .build();

        Department requestNo2 = Department.builder()
                .name("Test Department NO2")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO2").code("998").build()))
                .build();

        repository.saveAll(List.of(requestNo1, requestNo2));

        mockMvc.perform(get(API_URI)
                        .queryParam("description", "Wont be match"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("length()").value(0));
    }

    @Test
    void should_get_departments_return_all_departments_page_name_filtered() throws Exception {
        Department requestNo1 = Department.builder()
                .name("Test Department NO1")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO1").code("999").build()))
                .build();

        Department requestNo2 = Department.builder()
                .name("Test Department NO2")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO2").code("998").build()))
                .build();

        DepartmentFilterDto filter = DepartmentFilterDto.builder().name("Test Department NO1").build();

        repository.saveAll(List.of(requestNo1, requestNo2));

        mockMvc.perform(get(API_URI)
                        .queryParam("name", filter.name())
                        .param("size","2")
                        .param("page","0"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.content.[0].specialties[0].name").value("Test NO1"))
                .andExpect(jsonPath("$.size").value(2));
    }
    @Test
    void should_get_departments_return_all_departments_page_description_filtered() throws Exception {
        Department requestNo1 = Department.builder()
                .name("Test Department NO1")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO1").code("999").build()))
                .build();

        Department requestNo2 = Department.builder()
                .name("Test Department NO2")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO2").code("998").build()))
                .build();

        repository.saveAll(List.of(requestNo1, requestNo2));

        mockMvc.perform(get(API_URI)
                        .param("size","2")
                        .param("page","0")
                        .queryParam("description", "Test"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.size").value(2));
    }
    @Test
    void should_get_departments_return_all_departments_page_specialty_filtered() throws Exception {
        Specialty specialty1 = Specialty.builder().name("Test NO1").code("999").build();
        Specialty specialty2 = Specialty.builder().name("Test NO2").code("998").build();

        specialtyRepository.saveAll(List.of(specialty1,specialty2));
        Department requestNo1 = Department.builder()
                .name("Test Department NO1")
                .description("Mockito Test Department")
                .specialties(List.of(specialty1))
                .build();

        Department requestNo2 = Department.builder()
                .name("Test Department NO2")
                .description("Mockito Test Department")
                .specialties(List.of(specialty2))
                .build();

        requestNo1 = repository.save(requestNo1);
        requestNo2 = repository.save(requestNo2);
        specialty1.setDepartment(requestNo1);
        specialty2.setDepartment(requestNo2);
        specialtyRepository.saveAll(List.of(specialty1,specialty2));

        mockMvc.perform(get(API_URI)
                        .param("size","2")
                        .param("page","0")
                        .queryParam("specialties","999"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.content.[0].specialties[0].name").value("Test NO1"))
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.size").value(2));
    }
    @Test
    void should_get_departments_return_empty_page_when_filter_is_not_match() throws Exception {
        Department requestNo1 = Department.builder()
                .name("Test Department NO1")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO1").code("999").build()))
                .build();

        Department requestNo2 = Department.builder()
                .name("Test Department NO2")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO2").code("998").build()))
                .build();

        repository.saveAll(List.of(requestNo1, requestNo2));

        mockMvc.perform(get(API_URI)
                        .param("size","2")
                        .param("page","0")
                        .queryParam("description", "Wont be match"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.content.length()").value(0));
    }
    // </editor-fold>

    //  <editor-fold desc="Get Department By ID Tests">
    @Test
    void should_get_department_by_id_is_successful() throws Exception {
        Department department = Department.builder()
                .name("Test Department NO1")
                .description("Mockito Test Department")
                .specialties(List.of(Specialty.builder().name("Test NO1").code("999").build()))
                .build();


        Long departmentId =  repository.save(department).getId();

        mockMvc.perform(get(API_URI+"/"+departmentId))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.specialties.length()").value(department.getSpecialties().size()));
    }
    @Test
    void should_get_department_by_id_throw_exception_when_not_found() throws Exception {
        mockMvc.perform(get(API_URI+"/"+999L))
                .andExpect(status().is(Integer.parseInt(ErrorCode.RECORD_NOT_FOUND.getStatusCode())))
                .andExpect(jsonPath("$.message").value("Department not found with id: 999"))
                .andExpect(jsonPath("$.errorCode").value("RECORD_NOT_FOUND"));
    }
    //  </editor-fold>

    //  <editor-fold desc="Get Department By ID Tests">
    @Test
    void should_update_department_successfully() throws Exception {
        Specialty specialty1 = specialtyRepository.findByCode("001").get();
        Specialty specialty2 = specialtyRepository.findByCode("000").get();
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(specialty1);
        specialties.add(specialty2);

        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(specialties)
                .build();

        department = repository.save(department);

        DepartmentRequestDto requestDto = DepartmentRequestDto.builder()
                .name("Updated Department")
                .description("Mockito Update Test Department")
                .build();


        String content = mapper.writeValueAsString(requestDto);

        mockMvc.perform(put(API_URI+"/"+department.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.name").value("Updated Department"))
                .andExpect(jsonPath("$.specialties.length()").value(2))
                .andExpect(jsonPath("$.description").value("Mockito Update Test Department"));
    }

    @Test
    void should_update_department_throw_exception_when_id_is_invalid() throws Exception {
        Specialty specialty1 = specialtyRepository.findByCode("001").get();
        Specialty specialty2 = specialtyRepository.findByCode("000").get();
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(specialty1);
        specialties.add(specialty2);

        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(specialties)
                .build();

        repository.save(department);

        DepartmentRequestDto requestDto = DepartmentRequestDto.builder()
                .name("Updated Department")
                .description("Mockito Update Test Department")
                .build();


        String content = mapper.writeValueAsString(requestDto);

        mockMvc.perform(put(API_URI+"/"+"999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(Integer.parseInt(ErrorCode.RECORD_NOT_FOUND.getStatusCode())))
                .andExpect(jsonPath("$.message").value("Department not found with id: 999"))
                .andExpect(jsonPath("$.errorCode").value("RECORD_NOT_FOUND"));
    }
    //</editor-fold>

    // <editor-fold desc="Department Specialties Operation Tests">
    @Test
    void should_add_specialties_add_single_specialty_successfully() throws Exception {
        Specialty specialty1 = specialtyRepository.findByCode("001").get();
        Specialty specialty2 = specialtyRepository.findByCode("000").get();
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(specialty1);
        specialties.add(specialty2);

        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(specialties)
                .build();

        department = repository.save(department);

        Specialty newSpecialty = Specialty.builder().name("Hematology").code("002").build();
        specialtyRepository.save(newSpecialty);
        String content = mapper.writeValueAsString(List.of("002"));

        mockMvc.perform(patch(API_URI+"/"+department.getId()+"/add-specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.specialties.length()").value(3))
                .andExpect(jsonPath("$.specialties.[2].name").value("Hematology"));
    }

    @Test
    void should_add_specialties_add_multiple_specialty_successfully() throws Exception {
        Specialty specialty1 = specialtyRepository.findByCode("000").get();
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(specialty1);

        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(specialties)
                .build();

        department = repository.save(department);

        Specialty newSpecialty = Specialty.builder().name("Hematology").code("002").build();
        specialtyRepository.save(newSpecialty);
        String content = mapper.writeValueAsString(List.of("001","002"));

        mockMvc.perform(patch(API_URI+"/"+department.getId()+"/add-specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.specialties.length()").value(3))
                .andExpect(jsonPath("$.specialties.[2].name").value("Hematology"));
    }

    @Test
    void should_add_specialties_throw_exception_when_specialty_not_found() throws Exception {
        Specialty specialty1 = specialtyRepository.findByCode("000").get();
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(specialty1);

        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(specialties)
                .build();

        department = repository.save(department);

        String content = mapper.writeValueAsString(List.of("001","002"));

        mockMvc.perform(patch(API_URI+"/"+department.getId()+"/add-specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(Integer.parseInt(ErrorCode.RECORD_NOT_FOUND.getStatusCode())))
                .andExpect(jsonPath("$.message").value("Some of the given specialties could not be found. Please check the specialty codes and try again. Check List: [002]"))
                .andExpect(jsonPath("$.errorCode").value("RECORD_NOT_FOUND"));
    }

    @Test
    void should_remove_specialties_remove_single_specialty_successfully() throws Exception {
        Specialty specialty1 = specialtyRepository.findByCode("001").get();
        Specialty specialty2 = specialtyRepository.findByCode("000").get();
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(specialty1);
        specialties.add(specialty2);

        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(specialties)
                .build();

        department = repository.save(department);
        String content = mapper.writeValueAsString(List.of("000"));

        mockMvc.perform(patch(API_URI+"/"+department.getId()+"/remove-specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.specialties.length()").value(1))
                .andExpect(jsonPath("$.specialties.[0].name").value("Cardiology"));
    }

    @Test
    void should_remove_specialties_remove_multiple_specialty_successfully() throws Exception {
        Specialty specialty1 = specialtyRepository.findByCode("001").get();
        Specialty specialty2 = specialtyRepository.findByCode("000").get();
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(specialty1);
        specialties.add(specialty2);

        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(specialties)
                .build();

        department = repository.save(department);

        Specialty newSpecialty = Specialty.builder().name("Hematology").code("002").build();
        specialtyRepository.save(newSpecialty);
        String content = mapper.writeValueAsString(List.of("001","000"));

        mockMvc.perform(patch(API_URI+"/"+department.getId()+"/remove-specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.specialties.length()").value(0));
    }

    @Test
    void should_remove_specialties_throw_exception_when_specialty_not_found() throws Exception {
        Specialty specialty1 = specialtyRepository.findByCode("000").get();
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(specialty1);

        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(specialties)
                .build();

        department = repository.save(department);

        String content = mapper.writeValueAsString(List.of("002"));

        mockMvc.perform(patch(API_URI+"/"+department.getId()+"/remove-specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(Integer.parseInt(ErrorCode.RECORD_NOT_FOUND.getStatusCode())))
                .andExpect(jsonPath("$.message").value("Some of the given specialties could not be found. Please check the specialty codes and try again. Check List: [002]"))
                .andExpect(jsonPath("$.errorCode").value("RECORD_NOT_FOUND"));
    }
    //</editor-fold>

    //<editor-fold desc="Delete Department Tests">
    @Test
    void should_delete_department_successfully() throws Exception{
        Specialty specialty1 = specialtyRepository.findByCode("001").get();
        Specialty specialty2 = specialtyRepository.findByCode("000").get();
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(specialty1);
        specialties.add(specialty2);

        List<Specialty> beforeDelete = specialtyRepository.findAll();

        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(specialties)
                .build();

        department = repository.save(department);

        mockMvc.perform(delete(API_URI+"/"+department.getId()))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
        List<Specialty> afterDelete = specialtyRepository.findAll();

        Assert.isTrue(beforeDelete.size() == afterDelete.size(),"Specialties are not deleted.");
    }

    @Test
    void should_delete_department_throw_exception_when_id_is_invalid() throws Exception {
        Specialty specialty1 = specialtyRepository.findByCode("000").get();
        List<Specialty> specialties = new ArrayList<>();
        specialties.add(specialty1);

        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(specialties)
                .build();

        repository.save(department);

        mockMvc.perform(delete(API_URI+"/"+999L))
                .andExpect(status().is(Integer.parseInt(ErrorCode.RECORD_NOT_FOUND.getStatusCode())))
                .andExpect(jsonPath("$.message").value("Department not found with id: 999"))
                .andExpect(jsonPath("$.errorCode").value("RECORD_NOT_FOUND"));
    }
    //</editor-fold>

}
