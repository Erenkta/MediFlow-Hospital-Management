package com.hospital.mediflow.Common.Specifications;

import com.hospital.mediflow.Appointment.Domain.Entity.Appointment;
import com.hospital.mediflow.DoctorDepartments.Domain.Entity.DoctorDepartment;
import com.hospital.mediflow.Patient.Domain.Dtos.PatientFilterDto;
import com.hospital.mediflow.Patient.Domain.Entity.Patient;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class PatientSpecification extends BaseSpecification<Patient> {

    public static Specification<Patient> hasFirstName(String firstName) {
        String likeQuery = "%"+firstName+"%";
        return (root, query, criteriaBuilder) ->
                firstName == null ? null : criteriaBuilder.like(root.get("firstName"),likeQuery);
    }
    public static Specification<Patient> hasLastName(String lastName) {
        String likeQuery = "%"+lastName+"%";
        return (root, query, criteriaBuilder) ->
                lastName == null ? null : criteriaBuilder.like(root.get("lastName"),likeQuery);
    }
    public static Specification<Patient> hasBirthDateBefore(LocalDate date) {
        return (root, query, criteriaBuilder) ->
            date == null ?  null : criteriaBuilder.lessThanOrEqualTo(root.get("birthDate"), date);
    }
    public static Specification<Patient> hasBirthDateAfter(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                date == null ?  null : criteriaBuilder.greaterThanOrEqualTo(root.get("birthDate"), date);
    }
    public static Specification<Patient> hasBirthFilter(LocalDate startDate, LocalDate endDate) {
        return hasBirthDateBefore(endDate).and(hasBirthDateAfter(startDate));
    }
    public static Specification<Patient> hasBloodGroup(List<String> bloodGroup) {
        return (root, query, cb) -> {
            if (bloodGroup == null || bloodGroup.isEmpty()) return cb.conjunction();

            Predicate predicate = cb.disjunction();
            for (String value : bloodGroup) {
                predicate = cb.or(predicate, cb.like(root.get("bloodGroup"), "%" + value + "%"));
            }
            return predicate;
        };
    }
    public static Specification<Patient> hasGender(String gender) {
        return (root, query, criteriaBuilder) ->
                gender == null ? null : criteriaBuilder.like(root.get("gender"),gender);
    }

    public static Specification<Patient> withDoctorId(Long doctorId){
        return (root,query,cb) ->{
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Appointment> appt = subquery.from(Appointment.class);

            subquery
                    .select(appt.get("patient").get("id"))
                    .where(cb.equal(
                            appt.get("doctor").get("id"),
                            doctorId
                    ));

            return root.get("id").in(subquery);
        };
    }
    public static Specification<Patient> withDepartmentId(Long departmentId){
        return (root,query,cb) ->{

            Subquery<Long> doctorSubquery = query.subquery(Long.class);
            Root<DoctorDepartment> dd = doctorSubquery.from(DoctorDepartment.class);

            doctorSubquery
                    .select(dd.get("doctor").get("id"))
                    .where(cb.equal(
                            dd.get("department").get("id"),
                            departmentId
                    ));


            Subquery<Long> patientSubquery = query.subquery(Long.class);
            Root<Appointment> appt = patientSubquery.from(Appointment.class);

            patientSubquery
                    .select(appt.get("patient").get("id"))
                    .where(appt.get("doctor").get("id").in(doctorSubquery));

            return root.get("id").in(patientSubquery);
        };
    }
    public static Specification<Patient> hasFilter(PatientFilterDto filter){
        return Specification.allOf(
                hasFirstName(filter.firstName()),
                hasLastName(filter.lastName()),
                hasBirthFilter(filter.birthAfter(),filter.birthBefore()),
                hasBloodGroup(filter.bloodGroup()),
                hasGender(filter.gender())
        );
    }
}
