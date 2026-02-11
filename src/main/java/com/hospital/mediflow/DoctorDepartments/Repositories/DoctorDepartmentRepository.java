package com.hospital.mediflow.DoctorDepartments.Repositories;

import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.IncompatibleDoctorProjection;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentId;
import com.hospital.mediflow.DoctorDepartments.Domain.Entity.DoctorDepartment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
        SELECT EXISTS (SELECT 1 FROM DoctorDepartment docDep where docDep.id.doctorId = :doctorId)
    """)
    boolean isDoctorAssigned(@Param("doctorId") Long doctorId);


    @Query("""
    Select dd.id.doctorId from DoctorDepartment dd where dd.id.doctorId IN :doctorIds
""")
    List<Long> findAllAssignedDoctors(@Param("doctorIds") List<Long> doctorIds);

    @Query("""
    Delete from DoctorDepartment dd where dd.id.doctorId IN :doctorIds
""")
    @Modifying
    void deleteAllByDoctorId(@Param("doctorIds") List<Long> doctorIds);

    @Query("""
    Delete from DoctorDepartment docDep where docDep.id.doctorId = :doctorId
""")
    void deleteByDoctorId(@Param("doctorId") Long doctorId);

    @Query("""
    SELECT d.id AS id,
           d.firstName AS firstName,
           d.lastName AS lastName
    FROM Doctor d
    WHERE d.id IN :doctorIds
      AND d.specialty.code NOT IN (
          SELECT s.code
          FROM Specialty s
          WHERE s.department.id = :departmentId
      )
""")
    List<IncompatibleDoctorProjection> isDoctorSpecialtyCompatible(@Param("doctorIds") List<Long> doctorIds,
                                                                   @Param("departmentId") Long departmentId);

    @EntityGraph(attributePaths = {"department","doctor"}) // this is for n+1 problem. We get the DoctorDepartment,Doctor and Department with 1 fetch.
    List<DoctorDepartment> findAll(Specification<DoctorDepartment> spec);

    @Query("""
            Select count(dr) > 0 from DoctorDepartment dr 
            where dr.doctor.id = :doctor_id and dr.department.id = :department_id
            """)
    boolean isDepartmentDoctorRelationsExists(@Param("doctor_id") Long doctorId, @Param("department_id") Long departmentId);
}
