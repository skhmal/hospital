package com.khmal.hospital.dto;

import com.khmal.hospital.service.validator.CreateOrUpdateMarker;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class AppointmentDto {

    @Null(groups = CreateOrUpdateMarker.OnCreate.class)
    @NotNull(groups = CreateOrUpdateMarker.OnUpdate.class)
    private int id;

    @NotNull(message = "Field date must not be empty")
    private LocalDateTime date;

    @NotBlank(message = "Field appointment type must not be empty")
    private String appointmentType;

    @NotNull(message = "Field patient must not be empty")
    private PatientDto patient;

    @NotNull(message = "Field employee must not be empty")
    private HospitalStuffDto hospitalStuff;
}
