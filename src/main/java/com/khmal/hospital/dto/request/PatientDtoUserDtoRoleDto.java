package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PatientDtoUserDtoRoleDto {
    String firstName;
    String lastname;
    String username;
    String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;

    int roleId;
    int enabled;
    boolean discharged;
}
