package com.hospital.mediflow.Common.Specifications;

import com.hospital.mediflow.Department.Domain.Dtos.DepartmentFilterDto;
import com.hospital.mediflow.Department.Domain.Entity.Department;
import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class DepartmentSpecification extends BaseSpecification<Department> {

    public static Specification<Department> hasName(String name){
        return (root,query,criteriaBuilder) -> name == null ? null : criteriaBuilder.equal(root.get("name"), name);
    }
    public static Specification<Department> hasDescription(String description){
        return (root,query,criteriaBuilder) -> description == null ? null : criteriaBuilder.like(root.get("description"), "%"+description+"%");
    }
    public static Specification<Department> hasSpecialty(List<String> specialtyCodes){
        if(specialtyCodes.isEmpty()) return null;

        return (root,query,criteriaBuilder) ->{
            Join<Department, Specialty> specialtyJoin = root.join("specialties");
            return specialtyJoin.get("code").in(specialtyCodes);
            };
    }
    public static Specification<Department> filter(DepartmentFilterDto filter){
        return Specification.allOf(
                hasName(filter.name()),
                hasDescription(filter.description()),
                hasSpecialty(filter.specialties())
        );
    }

}
