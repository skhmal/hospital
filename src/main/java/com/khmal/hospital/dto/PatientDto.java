package com.khmal.hospital.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PatientDto {
    private Integer id;

    private String firstname;

    private String lastname;

    private String username;

    private LocalDate birthday;

    private boolean discharged;

    public PatientDto(String firstname, String lastname, String username, LocalDate birthday, boolean discharged) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.birthday = birthday;
        this.discharged = discharged;
    }
}
