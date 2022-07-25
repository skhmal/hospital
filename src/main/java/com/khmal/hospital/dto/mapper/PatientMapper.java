package com.khmal.hospital.dto.mapper;

import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dto.PatientDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    Patient toEntity(PatientDto patientDto);

    PatientDto toDto(Patient patient);

    List<PatientDto> toDto(List<Patient> patientList);

    List<Patient> toEntity(List<PatientDto> patientDtoList);
}
