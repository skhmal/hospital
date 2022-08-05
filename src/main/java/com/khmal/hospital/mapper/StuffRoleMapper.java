package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.StuffRole;
import com.khmal.hospital.dto.StuffRoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StuffRoleMapper {

    StuffRoleMapper INSTANCE = Mappers.getMapper(StuffRoleMapper.class);

    StuffRoleDto toDto(StuffRole stuffRole);

    StuffRole toEntity(StuffRoleDto stuffRoleDto);

    List<StuffRole> toEntity(List<StuffRoleDto> stuffRoleDtoList);

    List<StuffRoleDto>toDto(List<StuffRole> stuffRoleList);
}
