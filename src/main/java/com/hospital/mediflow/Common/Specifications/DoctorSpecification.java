package com.hospital.mediflow.Common.Specifications;

import com.hospital.mediflow.Doctor.Domain.Entities.Doctor;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DoctorSpecification extends BaseSpecification<Doctor> {

    public static Specification<Doctor>  hasTitle(TitleEnum title){
        return (root,query,criteriaBuilder) ->
               title == null ? null : criteriaBuilder.equal(root.get("title"), title);
    }
    public static Specification<Doctor>  hasSpecialty(String specialty){
        return (root,query,criteriaBuilder) ->
                specialty == null ? null :criteriaBuilder.equal(root.get("specialty").get("name"), specialty);
    }

    public static Specification<Doctor> hasDoctorCode(String specialty, TitleEnum title){
        return  hasTitle(title).and(hasSpecialty(specialty));
    }
}
