package com.hospital.mediflow.Common.Helpers.Predicate;

import com.hospital.mediflow.Doctor.Domain.Entities.QDoctor;
import com.hospital.mediflow.Doctor.Enums.SpecialtyEnum;
import com.hospital.mediflow.Doctor.Enums.TitleEnum;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DoctorPredicateBuilder {
    private final QDoctor qDoctor = QDoctor.doctor;
    private final List<BooleanExpression> predicates = new ArrayList<>();

    public DoctorPredicateBuilder withId(Integer id){
        if(id != null){
            predicates.add(qDoctor.id.eq(id));
        }
        return this;
    }

    public DoctorPredicateBuilder withFirstName(String firstName){
        if(firstName != null && !firstName.isBlank()){
            predicates.add(qDoctor.firstName.eq(firstName));
        }
        return this;
    }

    public DoctorPredicateBuilder withLastName(String lastName){
        if(lastName != null && !lastName.isBlank()){
            predicates.add(qDoctor.firstName.eq(lastName));
        }
        return this;
    }
    public DoctorPredicateBuilder withSpecialty(List<SpecialtyEnum> specialties){
        if (!specialties.isEmpty()) {
            predicates.add(qDoctor.specialty.in(specialties));
        }
        return this;
    }
    public DoctorPredicateBuilder withTitle(TitleEnum title){
        if (title != null) {
            predicates.add(qDoctor.title.eq(title));
        }
        return this;
    }

    public Predicate build(){
        return predicates.stream()
                .filter(Objects::nonNull)
                .reduce(BooleanExpression::and)
                .orElse(Expressions.TRUE);
    }

}
