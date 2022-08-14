package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Validated
public class PatientDtoUserDtoRoleDto {
    @NotNull(message = "Firstname can't be empty")
    String firstName;
    @NotNull(message = "Lastname can't be empty")
    String lastname;
    @NotNull(message = "Username can't be empty")
    String username;
    @NotNull(message = "Password can't be empty")
    String password;

    @NotNull(message = "Birthday can't be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;

    int roleId;
}
