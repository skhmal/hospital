package com.khmal.hospital.dto;

import com.khmal.hospital.entity.AppointmentType;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDto {
    private int id;
    private LocalDate date;
    private AppointmentType appointmentType;
    private PatientDto patient;
    private DoctorDto doctor;
}
