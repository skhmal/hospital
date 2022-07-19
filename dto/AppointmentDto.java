package com.khmal.hospital.dto;

import com.khmal.hospital.entity.HospitalStuff;
import com.khmal.hospital.entity.Patient;
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

    private Patient patient;

    private HospitalStuff hospitalStuff;
}
