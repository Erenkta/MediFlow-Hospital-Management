package com.hospital.mediflow.Doctor.Repositories;

import com.hospital.mediflow.Common.BaseRepository;
import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends BaseRepository<Doctor,Long>, QuerydslPredicateExecutor<Doctor> {

    List<Doctor> findAll(Predicate predicate);
    List<Doctor> findAll(Specification<Doctor> spec);
    Page<Doctor> findAll(Specification<Doctor> spec,Pageable pageable);
    boolean existsById(Long id);

}
