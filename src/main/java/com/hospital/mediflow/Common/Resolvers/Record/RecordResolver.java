package com.hospital.mediflow.Common.Resolvers.Record;

import com.hospital.mediflow.MedicalRecords.DataServices.Abstracts.MedicalRecordDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecordResolver {
    private final MedicalRecordDataService medicalRecordDataService;

    public Long resolveDoctorId(Long recordId) {
        return medicalRecordDataService.findDoctorIdByRecordId(recordId)
                .orElseThrow(() ->
                        new AccessDeniedException("Medical record not found")
                );
    }
}

