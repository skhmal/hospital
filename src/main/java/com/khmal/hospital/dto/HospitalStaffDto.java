package com.khmal.hospital.dto;

import com.khmal.hospital.service.validator.CreateOrUpdateMarker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HospitalStaffDto {

    @Null(groups = CreateOrUpdateMarker.OnCreate.class)
    @NotNull(groups = CreateOrUpdateMarker.OnUpdate.class)
    private Integer id;

    @NotBlank(message = "Field firstname must not be empty")
    private String firstname;

    @NotBlank(message = "Field lastname must not be empty")
    private String lastname;

    @NotBlank(message = "Field username must not be empty")
    private String username;

    @NotBlank(message = "Field role must not be empty")
    private String stuffRoleName;

    private String doctorSpecialization;

    private StaffRoleDto stuffRole;



    public HospitalStaffDto(String firstname, String lastname, String username, String doctorSpecialization, String stuffRoleName) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.doctorSpecialization = doctorSpecialization;
        this.stuffRoleName = stuffRoleName;
    }
}
