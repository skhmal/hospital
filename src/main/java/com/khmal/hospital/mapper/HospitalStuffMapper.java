package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dto.HospitalStuffDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HospitalStuffMapper {

    HospitalStuffMapper INSTANCE = Mappers.getMapper(HospitalStuffMapper.class);

    HospitalStuffDto toDto(HospitalStaff hospitalStaff);

    HospitalStaff toEntity(HospitalStuffDto hospitalStuffDto);

    List<HospitalStuffDto> toDto(List<HospitalStaff> hospitalStaff);
}
