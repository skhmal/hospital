package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class EmployeePatientAppointment {
    int hospitalStuffId;
    int patientId;
    String summary;
    String appointmentType;
    LocalDateTime appointmentDate;
}
