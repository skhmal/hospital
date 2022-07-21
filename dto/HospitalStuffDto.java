package com.khmal.hospital.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HospitalStuffDto {
    private Integer id;

    private String firstname;

    private String lastname;

    private String username;

    private String doctorSpecialization;

    private StuffRoleDto stuffRole;
}
