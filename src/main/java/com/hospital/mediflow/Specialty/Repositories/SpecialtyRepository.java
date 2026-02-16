package com.hospital.mediflow.Specialty.Repositories;

import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty,String> {
    Optional<Specialty> findByCode(String code);

    List<Specialty> findAllByOrderByCodeAsc();

    @Query(value = """
        SELECT COUNT(*) FROM mediflow_schema.specialties where code !='000'
    """,nativeQuery = true)
    Integer countSpecialties();

    @Query("""
    Select count(s) > 0 from Specialty s where s.code = :code and s.department.id = :department_id
""")
    boolean isSpecialtyDepartmentRelationExists(@Param("code") String specialtyCode,@Param("department_id") Long departmentId);
}
