package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.StaffRole;
import com.khmal.hospital.dto.StaffRoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StaffRoleMapper {

    StaffRoleMapper INSTANCE = Mappers.getMapper(StaffRoleMapper.class);

    StaffRoleDto toDto(StaffRole staffRole);

    StaffRole toEntity(StaffRoleDto staffRoleDto);

    List<StaffRole> toEntity(List<StaffRoleDto> staffRoleDtoList);

    List<StaffRoleDto>toDto(List<StaffRole> staffRoleList);
}
