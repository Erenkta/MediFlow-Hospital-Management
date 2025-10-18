package com.hospital.mediflow.DoctorDepartment.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.mediflow.Common.Exceptions.ErrorCode;
import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Department.Repository.DepartmentRepository;
import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import com.hospital.mediflow.Doctor.Repositories.DoctorRepository;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentId;
import com.hospital.mediflow.DoctorDepartments.Domain.Entity.DoctorDepartment;
import com.hospital.mediflow.DoctorDepartments.Repositories.DoctorDepartmentRepository;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import com.hospital.mediflow.Specialty.Repositories.SpecialtyRepository;
import jakarta.persistence.EntityManager;
import org.checkerframework.checker.units.qual.A;
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

import java.util.HashSet;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class DoctorDepartmentIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DoctorDepartmentRepository repository;
    @Autowired
    private SpecialtyRepository specialtyRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ObjectMapper mapper;

    private final String API_URI ="/api/v1/doctor-department";
    @Autowired
    private DoctorDepartmentRepository doctorDepartmentRepository;

    @BeforeEach
    public void insertSpecialties(){
        Specialty specialty1 = Specialty.builder().name("Hematology").code("003").build();
        Specialty specialty2 = Specialty.builder().name("Immunology").code("005").build();
        Specialty specialty3 = Specialty.builder().name("Plastic Surgery").code("009").build();

        specialtyRepository.saveAll(List.of(specialty1, specialty2,specialty3));
    }




    //  <editor-fold desc="Assign Doctors to department Test">
    @Test
    void should_sign_a_doctor_to_department_successfully() throws Exception {
        Specialty specialty = specialtyRepository.findByCode("005").get();
        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(List.of(specialty))
                .build();

        department = departmentRepository.save(department);

        specialty.setDepartment(department);
        specialty= specialtyRepository.save(specialty);

        Doctor doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(specialty)
                .title(TitleEnum.INTERN)
                .build();
        doctor = doctorRepository.save(doctor);

        String content = mapper.writeValueAsString(List.of(doctor.getId()));

        mockMvc.perform(
                        post(API_URI+"/"+department.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(jsonPath("$.departmentId").value(department.getId()))
                .andExpect(jsonPath("$.departmentName").value(department.getName()))
                .andExpect(jsonPath("$.doctors.length()").value(1))
                .andExpect(jsonPath("$.doctors.[0].doctorCode").value(doctor.getDoctorCode()))
                .andExpect(jsonPath("$.departmentDescription").value(department.getDescription()));
    }
    @Test
    void should_sign_multiple_doctors_to_department_successfully() throws Exception {
        Specialty specialty = specialtyRepository.findByCode("005").get();
        Specialty secondSpecialty = specialtyRepository.findByCode("003").get();
        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(List.of(specialty))
                .build();

        department = departmentRepository.save(department);

        specialty.setDepartment(department);
        secondSpecialty.setDepartment(department);
        specialty = specialtyRepository.save(specialty);
        secondSpecialty = specialtyRepository.save(secondSpecialty);

        Doctor doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(specialty)
                .title(TitleEnum.INTERN)
                .build();

        Doctor secondDoctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@doe.com")
                .phone("0523324512")
                .specialty(secondSpecialty)
                .title(TitleEnum.ASSISTANT)
                .build();

        doctor = doctorRepository.save(doctor);
        secondDoctor = doctorRepository.save(secondDoctor);

        String content = mapper.writeValueAsString(List.of(doctor.getId(),secondDoctor.getId()));

        mockMvc.perform(
                        post(API_URI+"/"+department.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(jsonPath("$.departmentId").value(department.getId()))
                .andExpect(jsonPath("$.departmentName").value(department.getName()))
                .andExpect(jsonPath("$.doctors.length()").value(2))
                .andExpect(jsonPath("$.doctors.[0].doctorCode").value(doctor.getDoctorCode()))
                .andExpect(jsonPath("$.departmentDescription").value(department.getDescription()));
    }

    @Test
    void should_sign_a_doctor_to_department_throw_exception_when_specialty_is_incompatible() throws Exception {
        Specialty specialty = specialtyRepository.findByCode("009").get();
        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(List.of(specialty))
                .build();

        department = departmentRepository.save(department);

        Doctor doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(specialty)
                .title(TitleEnum.INTERN)
                .build();
        doctor = doctorRepository.save(doctor);

        String content = mapper.writeValueAsString(List.of(doctor.getId()));
        mockMvc.perform(
                        post(API_URI+"/"+department.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().is(Integer.parseInt(ErrorCode.DOCTOR_IS_NOT_SUITABLE_FOR_DEPARTMENT.getStatusCode())))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.errorCode").value("DOCTOR_IS_NOT_SUITABLE_FOR_DEPARTMENT"));
    }
    @Test
    void should_sign_multiple_doctors_to_department_throw_exception_when_specialty_is_incompatible() throws Exception {
        Specialty specialty = specialtyRepository.findByCode("005").get();
        Specialty secondSpecialty = specialtyRepository.findByCode("009").get();
        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(List.of(specialty))
                .build();

        department = departmentRepository.save(department);

        specialty.setDepartment(department);
        specialty = specialtyRepository.save(specialty);

        Doctor doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(specialty)
                .title(TitleEnum.INTERN)
                .build();

        Doctor secondDoctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@doe.com")
                .phone("0523324512")
                .specialty(secondSpecialty)
                .title(TitleEnum.ASSISTANT)
                .build();

        doctor = doctorRepository.save(doctor);
        secondDoctor = doctorRepository.save(secondDoctor);

        String content = mapper.writeValueAsString(List.of(doctor.getId(),secondDoctor.getId()));

        mockMvc.perform(
                        post(API_URI+"/"+department.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().is(Integer.parseInt(ErrorCode.DOCTOR_IS_NOT_SUITABLE_FOR_DEPARTMENT.getStatusCode())))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.errorCode").value("DOCTOR_IS_NOT_SUITABLE_FOR_DEPARTMENT"));
    }
    // </editor-fold>

    //  <editor-fold desc="Remove Doctors from department Test">
    @Test
    void should_remove_a_doctor_from_department_successfully() throws Exception{
        Specialty specialty = specialtyRepository.findByCode("005").get();
        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(List.of(specialty))
                .build();

        department = departmentRepository.save(department);

        specialty.setDepartment(department);
        specialty= specialtyRepository.save(specialty);

        Doctor doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(specialty)
                .title(TitleEnum.INTERN)
                .build();
        doctor = doctorRepository.save(doctor);

        DoctorDepartment doctorDepartment = DoctorDepartment.builder()
                .id(new DoctorDepartmentId(doctor.getId(),department.getId()))
                .department(department)
                .doctor(doctor)
                .build();
        doctorDepartmentRepository.save(doctorDepartment);


        String content = mapper.writeValueAsString(List.of(doctor.getId()));

        mockMvc.perform(delete(API_URI+"/"+department.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.doctors.length()").value(0))
                .andExpect(jsonPath(("$.departmentId")).value(department.getId()))
                .andExpect(jsonPath(("$.departmentName")).value(department.getName()));
    }
    @Test
    void should_remove_multiple_doctors_from_department_successfully() throws Exception{
        Specialty specialty = specialtyRepository.findByCode("005").get();
        Specialty secondSpecialty = specialtyRepository.findByCode("009").get();
        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(List.of(specialty))
                .build();

        department = departmentRepository.save(department);

        specialty.setDepartment(department);
        specialty = specialtyRepository.save(specialty);

        Doctor doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(specialty)
                .title(TitleEnum.INTERN)
                .build();

        Doctor secondDoctor = Doctor.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@doe.com")
                .phone("0523324512")
                .specialty(secondSpecialty)
                .title(TitleEnum.ASSISTANT)
                .build();

        doctor = doctorRepository.save(doctor);
        secondDoctor = doctorRepository.save(secondDoctor);

        DoctorDepartment doctorDepartment = DoctorDepartment.builder()
                .id(new DoctorDepartmentId(doctor.getId(),department.getId()))
                .department(department)
                .doctor(doctor)
                .build();
        DoctorDepartment secondDoctorDepartment = DoctorDepartment.builder()
                .id(new DoctorDepartmentId(secondDoctor.getId(),department.getId()))
                .department(department)
                .doctor(secondDoctor)
                .build();

        doctorDepartmentRepository.save(doctorDepartment);
        doctorDepartmentRepository.save(secondDoctorDepartment);

        String content = mapper.writeValueAsString(List.of(doctor.getId(),secondDoctor.getId()));

        mockMvc.perform(delete(API_URI+"/"+department.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.doctors.length()").value(0))
                .andExpect(jsonPath(("$.departmentId")).value(department.getId()))
                .andExpect(jsonPath(("$.departmentName")).value(department.getName()));
    }

    @Test
    void should_remove_a_doctor_from_department_throw_exception_when_doctor_is_invalid() throws Exception{
        Specialty specialty = specialtyRepository.findByCode("005").get();
        Department department = Department.builder()
                .name("Test Department")
                .description("Mockito Test Department")
                .specialties(List.of(specialty))
                .build();

        department = departmentRepository.save(department);

        specialty.setDepartment(department);
        specialty= specialtyRepository.save(specialty);

        Doctor doctor = Doctor.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phone("0555324512")
                .specialty(specialty)
                .title(TitleEnum.INTERN)
                .build();
        doctor = doctorRepository.save(doctor);


        String content = mapper.writeValueAsString(List.of(doctor.getId()));
        String errorMessage =  String.format("No relation has found with doctor id: %s and department id :%s", doctor.getId(), department.getId());
        mockMvc.perform(delete(API_URI+"/"+department.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(Integer.parseInt(ErrorCode.RECORD_NOT_FOUND.getStatusCode())))
                .andExpect(jsonPath("$.message").value(errorMessage))
                .andExpect(jsonPath(("$.errorCode")).value("RECORD_NOT_FOUND"));
    }
    // </editor-fold>

    //<editor-fold desc="Get Doctor Department Tests">
    //</editor-fold>
}
