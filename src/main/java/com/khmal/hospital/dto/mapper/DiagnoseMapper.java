package com.khmal.hospital.dto.mapper;

import com.khmal.hospital.dao.entity.Diagnose;
import com.khmal.hospital.dto.DiagnoseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface DiagnoseMapper {

    DiagnoseMapper INSTANCE = Mappers.getMapper(DiagnoseMapper.class);

    DiagnoseDto toDto(Diagnose diagnose);

    Diagnose toEntity(DiagnoseDto diagnoseDto);
}
