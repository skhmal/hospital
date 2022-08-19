package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dto.AppointmentDto;
import org.springframework.data.domain.Page;

public class AppointmentPaginationMapper {
    public static Page<AppointmentDto> toDto(Page<Appointment> appointments) {
        return appointments.map(appointment ->
                new AppointmentDto(
                        appointment.getId(),
                        appointment.getDate(),
                        appointment.getAppointmentType(),
                        PatientMapper.INSTANCE.toDto(appointment.getPatient()),
                        appointment.getSummary(),
                        HospitalStuffMapper.INSTANCE.toDto(appointment.getHospitalStaff())));
    }
}
