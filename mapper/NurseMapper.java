package com.khmal.hospital.mapper;

import com.khmal.hospital.dto.NurseDto;
import com.khmal.hospital.entity.Nurse;
import org.mapstruct.Mapper;

@Mapper
public interface NurseMapper {

    NurseDto toDto(Nurse nurse);
}
