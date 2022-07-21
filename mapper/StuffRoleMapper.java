package com.khmal.hospital.mapper;

import com.khmal.hospital.dto.StuffRoleDto;
import com.khmal.hospital.entity.StuffRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StuffRoleMapper {

    StuffRoleMapper INSTANCE = Mappers.getMapper(StuffRoleMapper.class);

    StuffRoleDto toDto(StuffRole stuffRole);

    StuffRole toEntity(StuffRoleDto stuffRoleDto);
}
