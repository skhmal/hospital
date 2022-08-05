package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HospitalStuffDtoUserDtoRoleDto {
    String firstname;
    String lastname;
    String username;
    String password;
    String doctorSpecialization;
    int stuffRoleId;
}
