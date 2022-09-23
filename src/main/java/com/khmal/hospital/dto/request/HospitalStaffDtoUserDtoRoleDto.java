package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class HospitalStaffDtoUserDtoRoleDto {
    @NotBlank(message = "Firstname can't be empty")
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$", message = "First name must contains only letters")
    String firstname;

    @NotBlank(message = "Lastname can't be empty")
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$", message = "Last name must contains only letters")
    String lastname;

    @NotBlank(message = "Username can't be empty")
    String username;

    @NotBlank(message = "Password can't be empty")
    String password;

    String doctorSpecialization;
    int stuffRoleId;
}
