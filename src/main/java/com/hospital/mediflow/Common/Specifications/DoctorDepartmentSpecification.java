package com.hospital.mediflow.Common.Specifications;

import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.DoctorDepartments.Domain.Entity.DoctorDepartment;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class DoctorDepartmentSpecification extends BaseSpecification<DoctorDepartment> {

    public static Specification<DoctorDepartment> hasDepartmentName(String departmentName){
        return (root,query,criteriaBuilder) ->{
            Join<DoctorDepartment, Department > departmentJoin = root.join("department");
            return departmentName == null ? null : criteriaBuilder.like(departmentJoin.get("name"), departmentName);
        };
    }
    public static Specification<DoctorDepartment> hasDepartmentDescription(String departmentDescription){
        return (root,query,criteriaBuilder) ->{
            Join<DoctorDepartment,Department> departmentJoin = root.join("department");
            return departmentDescription == null ? null : criteriaBuilder.like(departmentJoin.get("description"), departmentDescription);
        };
    }
    public static Specification<Department> hasMinDoctorCount(Long departmentId, Long doctorCount) {
        return (root, query, cb) -> {
            Join<Department, DoctorDepartment> doctorJoin = root.join("doctorDepartments", JoinType.LEFT);
            query.groupBy(root.get("id"));
            return cb.and(
                    cb.equal(root.get("id"), departmentId),
                    cb.greaterThanOrEqualTo(cb.count(doctorJoin), doctorCount)
            );
        };
    }
    public static Specification<DoctorDepartment> hasSpecialty(List<String> specialties){
        if(specialties == null|| specialties.isEmpty()) return null;

        return (root,query,criteriaBuilder) ->{
            Join<DoctorDepartment, Department> departmentJoin = root.join("department");
            Join<Department, String> specialtiesJoin = departmentJoin.join("specialties");

            return specialtiesJoin.in(specialties);
        };

    }
}
