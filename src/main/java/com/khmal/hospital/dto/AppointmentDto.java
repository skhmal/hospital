package com.khmal.hospital.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class AppointmentDto {
    private int id;

    private LocalDateTime date;

    private String appointmentType;

    private PatientDto patient;

    private HospitalStuffDto hospitalStuff;
}
