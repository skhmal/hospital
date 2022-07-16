package com.khmal.hospital.mapper;

import com.khmal.hospital.dto.NurseDto;
import com.khmal.hospital.entity.Nurse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NurseMapper {

    NurseMapper INSTANCE = Mappers.getMapper(NurseMapper.class);

    Nurse toEntity(NurseDto nurseDto);
    NurseDto toDto(Nurse nurse);
    List<NurseDto> toDto(List<Nurse> nurseList);
}
