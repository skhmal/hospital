package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dto.AppointmentDto;
import org.springframework.data.domain.Page;

import java.util.function.Function;

public class AppointmentPaginationMapper {
    public static Page<AppointmentDto> toDto(Page<Appointment> appointments){
        Page<AppointmentDto> appointmentDto = appointments.map(new Function<Appointment, AppointmentDto>() {
            @Override
            public AppointmentDto apply(Appointment appointment) {

                AppointmentDto appointmentDto = new AppointmentDto();

                appointmentDto.setId(appointment.getId());
                appointmentDto.setAppointmentType(appointment.getAppointmentType());
                appointmentDto.setDate(appointment.getDate());
                appointmentDto.setSummary(appointment.getSummary());
                appointmentDto.setPatient(
                        AppointmentMapper.INSTANCE.toDto(appointment).getPatient());
                appointmentDto.setHospitalStaff(
                        AppointmentMapper.INSTANCE.toDto(appointment).getHospitalStaff());

                return appointmentDto;
            }
        });
        return appointmentDto;
    }
}
