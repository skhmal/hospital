package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class HospitalStuffDtoUserDtoRoleDto {
    @NotBlank
    String firstname;
    @NotBlank
    String lastname;
    @NotBlank
    String username;
    @NotBlank
    String password;

    String doctorSpecialization;
    int stuffRoleId;
}
