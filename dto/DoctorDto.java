package com.khmal.hospital.dto;

import lombok.Data;

import java.util.List;

@Data
public class DoctorDto {
    Integer id;

    UserDto userPatient;

    List<PatientDto> patientList;
}
