package com.hospital.mediflow.Department.Repository;

import com.hospital.mediflow.Department.Domain.Entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findAll(Specification<Department> spec);
    Page<Department> findAll(Specification<Department> spec, Pageable pageable);
}
