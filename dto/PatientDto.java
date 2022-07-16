package com.khmal.hospital.dto;

import lombok.Data;

import java.util.List;

@Data
public class PatientDto {
    Integer id;

    UserDto userPatient;

    List<DoctorDto> doctorList;

}
