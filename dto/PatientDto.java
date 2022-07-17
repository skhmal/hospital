package com.khmal.hospital.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatientDto {
    Integer id;

    UserDto userPatient;

//    List<DoctorDto> doctorsList;

}
