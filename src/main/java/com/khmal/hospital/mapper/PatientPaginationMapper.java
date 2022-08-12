package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dto.PatientDto;
import org.springframework.data.domain.Page;

import java.util.function.Function;

public class PatientPaginationMapper {
    public static Page<PatientDto> toDto(Page<Patient> patients){
        Page<PatientDto> patientDtoPage = patients.map(new Function<Patient, PatientDto>() {
            @Override
            public PatientDto apply(Patient patient) {
                PatientDto patientDto = new PatientDto();
                patientDto.setId(patient.getId());
                patientDto.setFirstname(patient.getFirstname());
                patientDto.setLastname(patient.getLastname());
                patientDto.setUsername(patient.getUsername());
                patientDto.setBirthday(patient.getBirthday());
                return patientDto;
            }
        });
        return patientDtoPage;
    }
}
