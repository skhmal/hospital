package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Validated
public class AppointmentRequest {
    @NotNull(message = "Field doctor id can't be empty")
    int doctorId;
    @NotNull(message = "Field patient id can't be empty")
    int patientIdDoctorAppointment;

}
