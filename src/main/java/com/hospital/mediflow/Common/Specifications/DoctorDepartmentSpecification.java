package com.hospital.mediflow.Common.Specifications;

import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.hospital.mediflow.Doctor.Domain.Entities.QDoctor;
import com.hospital.mediflow.DoctorDepartments.Domain.Dtos.DoctorDepartmentFilterDto;
import com.hospital.mediflow.DoctorDepartments.Domain.Entity.DoctorDepartment;
import com.hospital.mediflow.DoctorDepartments.Domain.Entity.QDoctorDepartment;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class DoctorDepartmentSpecification extends BaseSpecification<DoctorDepartment> {

    public static Specification<DoctorDepartment> hasDepartmentName(String departmentName) {
        return (root, query, criteriaBuilder) -> {
            Join<DoctorDepartment, Department> departmentJoin = root.join("department");
            return departmentName == null ? null : criteriaBuilder.like(departmentJoin.get("name"), "%" + departmentName + "%");
        };
    }

    public static Specification<DoctorDepartment> hasDepartmentDescription(String departmentDescription) {
        return (root, query, criteriaBuilder) -> {
            Join<DoctorDepartment, Department> departmentJoin = root.join("department");
            return departmentDescription == null ? null : criteriaBuilder.like(departmentJoin.get("description"), "%" + departmentDescription + "%");
        };
    }

    public static Specification<DoctorDepartment> hasSpecialty(List<String> specialties) {
        if (specialties == null || specialties.isEmpty()) return null;

        return (root, query, criteriaBuilder) -> {
            Join<DoctorDepartment, Department> departmentJoin = root.join("department");
            Join<Department, Specialty> specialtiesJoin = departmentJoin.join("specialties");

            return specialtiesJoin.get("name").in(specialties);
        };
    }

    public static Specification<DoctorDepartment> hasDoctor(List<Long> doctorIds) {
        if (doctorIds == null || doctorIds.isEmpty()) return null;

        return (root, query, criteriaBuilder) -> {
            Join<DoctorDepartment, Doctor> doctorJoin = root.join("doctor");
            return doctorJoin.get("id").in(doctorIds);
        };
    }

    public static Specification<DoctorDepartment> hasFilter(DoctorDepartmentFilterDto filter) {
        return Specification.allOf(
                hasDepartmentName(filter.departmentName()),
                hasDepartmentDescription(filter.departmentDescription()),
                hasSpecialty(filter.specialties()),
                hasDoctor(filter.doctors())
        );
    }
}
