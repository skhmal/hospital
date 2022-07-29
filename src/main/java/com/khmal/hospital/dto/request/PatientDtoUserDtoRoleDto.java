package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class PatientDtoUserDtoRoleDto {
    String firstName;
    String lastname;
    String username;
    String password;
    LocalDate birthday;
    int roleId;
    int enabled;
    boolean discharged;
}
