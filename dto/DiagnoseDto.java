package com.khmal.hospital.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DiagnoseDto {
    private int id;
    private String summary;
    private LocalDate diagnoseDate;
    private PatientDto patient;
    private DoctorDto doctor;
}
