package com.khmal.hospital.dto;

import com.khmal.hospital.service.validator.CreateOrUpdateMarker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PatientDto {
    @Null(groups = CreateOrUpdateMarker.OnCreate.class)
    @NotNull(groups = CreateOrUpdateMarker.OnUpdate.class)
    private Integer id;

    @NotBlank(message = "Field firstname must not be empty")
    private String firstname;

    @NotBlank(message = "Field lastname must not be empty")
    private String lastname;

    @NotBlank(message = "Field username must not be empty")
    private String username;

    @NotNull(message = "Field birthday must not be empty")
    private LocalDate birthday;

    @NotNull(message = "Field discharged must not be empty")
    private boolean discharged;

    @NotNull(message = "Role must not be empty")
    private int roleId;

    public PatientDto(String firstname, String lastname, String username, LocalDate birthday, boolean discharged, int roleId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.birthday = birthday;
        this.discharged = discharged;
        this.roleId = roleId;
    }
}
