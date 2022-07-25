package com.khmal.hospital.dto;

import com.khmal.hospital.dao.entity.HospitalStuff;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class AppointmentDto {
    private int id;

    private LocalDate date;

    private String appointmentType;

    private PatientDto patient;

    private HospitalStuffDto hospitalStuff;
}
