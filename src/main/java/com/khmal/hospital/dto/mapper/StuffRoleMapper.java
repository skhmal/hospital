package com.khmal.hospital.dto.mapper;

import com.khmal.hospital.dao.entity.StuffRole;
import com.khmal.hospital.dto.StuffRoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StuffRoleMapper {

    StuffRoleMapper INSTANCE = Mappers.getMapper(StuffRoleMapper.class);

    StuffRoleDto toDto(StuffRole stuffRole);

    StuffRole toEntity(StuffRoleDto stuffRoleDto);
}
