package com.hospital.mediflow.Common.Queries.Manager;

import com.hospital.mediflow.Common.Annotations.Access.Manager.ManagerDoctorAccess;
import com.hospital.mediflow.Common.Annotations.Access.AccessType;
import com.hospital.mediflow.Common.Annotations.Access.ResourceType;
import com.hospital.mediflow.Common.Annotations.ResourceAccess;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorRequestDto;
import com.hospital.mediflow.Doctor.Domain.Dtos.DoctorResponseDto;
import com.hospital.mediflow.Doctor.Services.Abstracts.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ManagerDoctorQuery {
    private final DoctorService doctorService;

    @ManagerDoctorAccess(type=AccessType.CREATE)
    public DoctorResponseDto saveDoctor(DoctorRequestDto request) {
        return doctorService.saveDoctor(request);
    }

    @ManagerDoctorAccess(type=AccessType.UPDATE)
    public DoctorResponseDto updateDoctor(Long doctorId, DoctorRequestDto request) {
        DoctorRequestDto updateBody = request.ManagerRequest();
        return doctorService.updateDoctor(doctorId,updateBody);
    }

    //@ManagerDoctorAccess(type=AccessType.DELETE)
    @ResourceAccess(action=AccessType.DELETE,resource = ResourceType.DOCTOR)
    public void deleteDoctor(Long doctorId) {
        doctorService.deleteDoctor(doctorId);
    }
}
