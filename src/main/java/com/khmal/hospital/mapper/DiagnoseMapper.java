package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.Diagnose;
import com.khmal.hospital.dto.DiagnoseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DiagnoseMapper {

    DiagnoseMapper INSTANCE = Mappers.getMapper(DiagnoseMapper.class);

    DiagnoseDto toDto(Diagnose diagnose);

    Diagnose toEntity(DiagnoseDto diagnoseDto);

    List<Diagnose> toEntity(List<DiagnoseDto> diagnoseDtoList);

    List<DiagnoseDto> toDto(List<Diagnose> diagnoseList);
}
