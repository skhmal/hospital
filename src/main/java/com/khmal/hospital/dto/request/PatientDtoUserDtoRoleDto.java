package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PatientDtoUserDtoRoleDto {

    @NotBlank(message = "Firstname can't be empty")
    private String firstName;

    @NotBlank(message = "Lastname can't be empty")
    private String lastname;

    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "Password can't be empty")
    private String password;

    @NotNull(message = "Birthday can't be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private int roleId;
}
