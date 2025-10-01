package com.hospital.mediflow.Doctor.Repositories;

import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long>, QuerydslPredicateExecutor<Doctor> {


    Boolean existsByDoctorCode(Long doctorCode);

    List<Doctor> findAll(Predicate predicate);
    List<Doctor> findAll(Specification<Doctor> spec);
}
