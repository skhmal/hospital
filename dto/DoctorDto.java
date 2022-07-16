package com.khmal.hospital.dto;

import lombok.Data;

import java.util.List;

@Data
public class DoctorDto {
    Integer id;

    UserDto userDto;

    List<PatientDto> patientDtoList;
}
