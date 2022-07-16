package com.khmal.hospital.mapper;

import com.khmal.hospital.dto.AdministratorDto;
import com.khmal.hospital.entity.Administrator;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AdministratorMapper {

    AdministratorMapper INSTANCE = Mappers.getMapper(AdministratorMapper.class);

    AdministratorDto toDto(Administrator administrator);
    Administrator toEntity(AdministratorDto administratorDto);

    List<AdministratorDto> toDto(List<Administrator> administratorList);
}
