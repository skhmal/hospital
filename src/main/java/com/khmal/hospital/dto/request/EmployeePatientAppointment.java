package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@ToString
public class EmployeePatientAppointment {
    @NotNull
    @NotEmpty
    int hospitalStuffId;

    @NotNull
    @NotEmpty
    int patientId;

    @NotBlank
    String summary;

    @NotBlank
    String appointmentType;

    @NotNull
    @NotEmpty
    LocalDateTime appointmentDate;
}
