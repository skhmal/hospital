package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Validated
public class EmployeePatientAppointment {
    @NotNull
    int hospitalStuffId;

    @NotNull
    int patientId;

    @NotBlank(message = "Field summary can't be empty")
    String summary;

    @NotBlank(message = "Field appointment type can't be empty")
    String appointmentType;

    @NotNull(message = "Field date can't be empty")
    @NotEmpty
    LocalDateTime appointmentDate;
}
