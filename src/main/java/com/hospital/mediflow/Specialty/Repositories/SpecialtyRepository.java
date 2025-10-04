package com.hospital.mediflow.Specialty.Repositories;

import com.hospital.mediflow.Specialty.Domain.Entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty,String> {
    Optional<Specialty> findByCode(String code);
}
