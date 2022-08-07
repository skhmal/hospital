package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppointmentRequest {
    int doctorId;
    int patientIdDoctorAppointment;

}
