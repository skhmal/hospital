package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dto.HospitalStaffDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HospitalStuffMapper {

    HospitalStuffMapper INSTANCE = Mappers.getMapper(HospitalStuffMapper.class);

    HospitalStaffDto toDto(HospitalStaff hospitalStaff);

    HospitalStaff toEntity(HospitalStaffDto hospitalStaffDto);

    List<HospitalStaffDto> toDto(List<HospitalStaff> hospitalStaff);
}
