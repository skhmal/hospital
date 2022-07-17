package com.khmal.hospital.mapper;

import com.khmal.hospital.dto.DoctorDto;
import com.khmal.hospital.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DoctorMapper {

    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);
    DoctorDto toDto(Doctor doctor);
    Doctor toEntity(DoctorDto doctorDto);
    List<DoctorDto> toDto(List<Doctor> doctorList);
    List<Doctor> toEntity(List<DoctorDto> doctorDtoList);
}
