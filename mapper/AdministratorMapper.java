package com.khmal.hospital.mapper;

import com.khmal.hospital.dto.AdministratorDto;
import com.khmal.hospital.entity.Administrator;
import org.mapstruct.Mapper;

@Mapper
public interface AdministratorMapper {
    AdministratorDto toDto(Administrator administrator);
}
