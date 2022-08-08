package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dto.AppointmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AppointmentMapper {

    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    AppointmentDto toDto(Appointment appointment);

    Appointment toEntity(AppointmentDto appointmentDto);

    List<AppointmentDto> toDto(List<Appointment> appointmentList);

    List<Appointment> toEntity(List<AppointmentDto> appointmentDtoList);
}
