package com.khmal.hospital.mapper;

import com.khmal.hospital.dto.DoctorDto;
import com.khmal.hospital.entity.Doctor;
import org.mapstruct.Mapper;

@Mapper
public interface DoctorMapper {

    DoctorDto toDto(Doctor doctor);
}
