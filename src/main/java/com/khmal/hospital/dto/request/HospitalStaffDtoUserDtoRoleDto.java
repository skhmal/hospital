package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class HospitalStaffDtoUserDtoRoleDto {
    @NotBlank(message = "Firstname can't be empty")
    String firstname;

    @NotBlank(message = "Lastname can't be empty")
    String lastname;

    @NotBlank(message = "Username can't be empty")
    String username;

    @NotBlank(message = "Password can't be empty")
    String password;

    String doctorSpecialization;
    int stuffRoleId;
}
