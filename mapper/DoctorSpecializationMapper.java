package com.khmal.hospital.mapper;

import com.khmal.hospital.dto.DoctorSpecializationDto;
import com.khmal.hospital.entity.DoctorSpecialization;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface DoctorSpecializationMapper {

    DoctorSpecializationMapper INSTANCE = Mappers.getMapper(DoctorSpecializationMapper.class);

    DoctorSpecialization toEntity(DoctorSpecializationDto doctorSpecializationDto);

    DoctorSpecializationDto toDto(DoctorSpecialization doctorSpecialization);
}
