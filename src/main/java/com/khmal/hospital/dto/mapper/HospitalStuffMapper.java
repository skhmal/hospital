package com.khmal.hospital.dto.mapper;

import com.khmal.hospital.dao.entity.HospitalStuff;
import com.khmal.hospital.dto.HospitalStuffDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HospitalStuffMapper {

    HospitalStuffMapper INSTANCE = Mappers.getMapper(HospitalStuffMapper.class);

    HospitalStuffDto toDto(HospitalStuff hospitalStuff);

    HospitalStuff toEntity(HospitalStuffDto hospitalStuffDto);
}
