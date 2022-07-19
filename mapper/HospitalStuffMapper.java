package com.khmal.hospital.mapper;

import com.khmal.hospital.dto.HospitalStuffDto;
import com.khmal.hospital.entity.HospitalStuff;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HospitalStuffMapper {

    HospitalStuffMapper INSTANCE = Mappers.getMapper(HospitalStuffMapper.class);

    HospitalStuffDto toDto(HospitalStuff hospitalStuff);

    HospitalStuff toEntity(HospitalStuffDto hospitalStuffDto);
}
