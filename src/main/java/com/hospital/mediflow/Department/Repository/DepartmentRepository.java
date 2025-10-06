package com.hospital.mediflow.Department.Repository;

import com.hospital.mediflow.Department.Domain.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
