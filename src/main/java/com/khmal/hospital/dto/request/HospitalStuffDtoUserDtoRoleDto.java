package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HospitalStuffDtoUserDtoRoleDto {
    String firstname;
    String lastname;
    String username;
    String password;
    String doctorSpecialization;
    int stuffRoleId;
    int enabled;
}
