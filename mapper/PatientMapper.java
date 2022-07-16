package com.khmal.hospital.mapper;

import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.entity.Patient;
import org.mapstruct.Mapper;

@Mapper
public interface PatientMapper {
    PatientDto toDto(Patient patient);
}
