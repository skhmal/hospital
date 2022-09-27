package com.khmal.hospital.mapper;

import com.khmal.hospital.Helper;
import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dto.AppointmentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AppointmentMapperTest {

    @Autowired
    private AppointmentMapper appointmentMapper;

    private final Appointment APPOINTMENT = Helper.getAppointment();
    private final AppointmentDto APPOINTMENT_DTO = Helper.getAppointmentDto();

    @Test
    void toDto() {
        AppointmentDto appointmentDto = appointmentMapper.toDto(APPOINTMENT);

        assertEquals(APPOINTMENT.getSummary(), appointmentDto.getSummary());
        assertEquals(APPOINTMENT.getDate(), appointmentDto.getDate());
        assertEquals(APPOINTMENT.getPatient().getUsername(), appointmentDto.getPatient().getUsername());
        assertEquals(APPOINTMENT.getHospitalStaff().getLastname(), appointmentDto.getHospitalStaff().getLastname());
    }

    @Test
    void toEntity() {
        Appointment appointmentTest = appointmentMapper.toEntity(APPOINTMENT_DTO);

        assertEquals(APPOINTMENT_DTO.getHospitalStaff().getUsername(), appointmentTest.getHospitalStaff().getUsername());
        assertEquals(APPOINTMENT_DTO.getPatient().getBirthday(), appointmentTest.getPatient().getBirthday());
        assertEquals(APPOINTMENT_DTO.getDate(), appointmentTest.getDate());
    }

    @Test
    void testToDto() {
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(APPOINTMENT);

        List<AppointmentDto> appointmentDtoList = appointmentMapper.toDto(appointmentList);

        assertEquals(APPOINTMENT.getDate(), appointmentDtoList.get(0).getDate());
        assertEquals(APPOINTMENT.getSummary(), appointmentDtoList.get(0).getSummary());
        assertEquals(APPOINTMENT.getPatient().getBirthday(), appointmentDtoList.get(0).getPatient().getBirthday());
    }

    @Test
    void testToEntity() {
        List<AppointmentDto> appointmentDtoList = new ArrayList<>();
        appointmentDtoList.add(APPOINTMENT_DTO);

        List<Appointment> appointmentList = appointmentMapper.toEntity(appointmentDtoList);

        assertEquals(APPOINTMENT_DTO.getHospitalStaff().getFirstname(), appointmentList.get(0).getHospitalStaff().getFirstname());
        assertEquals(APPOINTMENT_DTO.getAppointmentType(), appointmentList.get(0).getAppointmentType());
    }
}