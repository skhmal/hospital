package com.khmal.hospital.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DoctorDto {
    Integer id;

    UserDto userDoctor;

    List<PatientDto> patientsList;
}
