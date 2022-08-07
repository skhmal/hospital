package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.HospitalStuff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dto.HospitalStuffDto;
import com.khmal.hospital.dto.PatientDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HospitalStuffMapper {

    HospitalStuffMapper INSTANCE = Mappers.getMapper(HospitalStuffMapper.class);

    HospitalStuffDto toDto(HospitalStuff hospitalStuff);

    HospitalStuff toEntity(HospitalStuffDto hospitalStuffDto);

    List<HospitalStuffDto> toDto(List<HospitalStuff> hospitalStuff);

}
