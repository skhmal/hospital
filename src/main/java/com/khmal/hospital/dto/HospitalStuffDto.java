package com.khmal.hospital.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HospitalStuffDto {
    private Integer id;

    private String firstname;

    private String lastname;

    private String username;

    private String doctorSpecialization;

    private StuffRoleDto stuffRole;

    private String stuffRoleName;

    public HospitalStuffDto(String firstname, String lastname, String username, String doctorSpecialization, String stuffRoleName) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.doctorSpecialization = doctorSpecialization;
        this.stuffRoleName = stuffRoleName;
    }
}
