package com.hospital.mediflow.DoctorDepartments.Repositories;

import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentId;
import com.hospital.mediflow.DoctorDepartments.Domain.Entity.DoctorDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorDepartmentRepository extends JpaRepository<DoctorDepartment, DoctorDepartmentId> {

    @Query("""
    SELECT DISTINCT new com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto(
        d.id,
        d.doctorCode,
        d.title,
        d.firstName,
        d.lastName,
        d.specialty.name, 
        d.phone,
        d.email
    )
    FROM Doctor d
    JOIN d.doctorDepartment dep
    WHERE dep.id.departmentId = :departmentId
""")
    List<DoctorResponseDto> findDepartmentDoctors(@Param("departmentId") Long departmentId);

    @Query("""
    SELECT DISTINCT d FROM Department d
    LEFT JOIN FETCH d.doctorDepartments dd
    LEFT JOIN FETCH dd.doctor doc
""")
    List<Department> findAllWithDoctors();

}
