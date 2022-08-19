package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dto.PatientDto;
import org.springframework.data.domain.Page;

public class PatientPaginationMapper {
    public static Page<PatientDto> toDto(Page<Patient> patients) {
        return patients.map(patient ->
                new PatientDto(
                        patient.getId(),
                        patient.getFirstname(),
                        patient.getLastname(),
                        patient.getUsername(),
                        patient.getBirthday(),
                        patient.isDischarged()
                ));
    }
}
