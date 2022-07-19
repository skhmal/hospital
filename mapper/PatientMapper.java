package com.khmal.hospital.mapper;

import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    Patient toEntity(PatientDto patientDto);

    PatientDto toDto(Patient patient);

//    List<PatientDto> toDto(List<Patient> patientList);
//
//    List<Patient> toEntity(List<PatientDto> patientDtoList);
}
