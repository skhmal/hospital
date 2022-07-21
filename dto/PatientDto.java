package com.khmal.hospital.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PatientDto {
    private Integer id;

    private String firstname;

    private String lastname;

    private String username;

    private LocalDate birthday;

    private boolean discharged;
}
