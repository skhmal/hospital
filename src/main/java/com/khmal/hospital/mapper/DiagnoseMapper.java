package com.khmal.hospital.mapper;

import com.khmal.hospital.dto.DiagnoseDto;
import com.khmal.hospital.entity.Diagnose;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface DiagnoseMapper {

    DiagnoseMapper INSTANCE = Mappers.getMapper(DiagnoseMapper.class);

    DiagnoseDto toDto(Diagnose diagnose);

    Diagnose toEntity(DiagnoseDto diagnoseDto);
}
