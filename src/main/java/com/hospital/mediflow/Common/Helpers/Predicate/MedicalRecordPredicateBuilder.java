package com.hospital.mediflow.Common.Helpers.Predicate;

import com.hospital.mediflow.Department.Domain.Entity.QDepartment;
import com.hospital.mediflow.Doctor.Domain.Entities.QDoctor;
import com.hospital.mediflow.MedicalRecords.Domain.Entity.QMedicalRecord;
import com.hospital.mediflow.Patient.Domain.Entity.QPatient;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MedicalRecordPredicateBuilder {
    private final QMedicalRecord qMedicalRecord = QMedicalRecord.medicalRecord;
    private List<BooleanExpression> predicates = new ArrayList<>();

    public MedicalRecordPredicateBuilder withDoctorId(Long doctorId) {
        if(doctorId != null){
            predicates.add(qMedicalRecord.doctor.id.eq(doctorId));
        }
        return this;
    }
    public MedicalRecordPredicateBuilder withPatientId(Long patientId) {
        if(patientId != null){
            predicates.add(qMedicalRecord.patient.id.eq(patientId));
        }
        return this;
    }
    public MedicalRecordPredicateBuilder withDoctorName(String doctorName) {
        if(!doctorName.isEmpty()){
            QDoctor qDoctor = qMedicalRecord.doctor;
            StringExpression fullName = qDoctor.firstName.concat(" ").concat(qDoctor.lastName);
            predicates.add(fullName.like(doctorName));
        }
        return this;
    }
    public MedicalRecordPredicateBuilder withPatientName(String patientName) {
        if(!patientName.isEmpty()){
            QPatient qPatient = qMedicalRecord.patient;
            StringExpression fullName = qPatient.firstName.concat(" ").concat(qPatient.lastName);
            predicates.add(fullName.like(patientName));
        }
        return this;
    }
    public MedicalRecordPredicateBuilder withDepartmentName(String departmentName) {
        if(!departmentName.isEmpty()){
            QDepartment qDepartment = qMedicalRecord.doctor.specialty.department;
            predicates.add(qDepartment.name.like(departmentName));
        }
        return this;
    }
    public MedicalRecordPredicateBuilder withRecordDate(LocalDateTime recordDateStart,LocalDateTime recordDateEnd){
        if(recordDateStart != null ){
            predicates.add(qMedicalRecord.recordDate.after(recordDateStart));
        }
        if (recordDateEnd != null) {
            predicates.add(qMedicalRecord.recordDate.before(recordDateEnd));
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
