package com.khmal.hospital.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DoctorDto {
    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private String doctorSpecialization;
    private int patientCount;

    public DoctorDto(int id, String firstname, String lastname, String username, String doctorSpecialization, int patientCount) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.doctorSpecialization = doctorSpecialization;
        this.patientCount = patientCount;
    }
}
