package com.hospital.mediflow.Department.Repository;

import com.hospital.mediflow.Common.BaseRepository;
import com.hospital.mediflow.Department.Domain.Entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends BaseRepository<Department, Long>  {
    Page<Department> findAll(Specification<Department> spec, Pageable pageable);
}
