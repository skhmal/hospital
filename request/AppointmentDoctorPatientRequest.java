package com.khmal.hospital.request;

import com.khmal.hospital.entity.AppointmentType;
import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.Patient;
import lombok.Getter;

@Getter
public class AppointmentDoctorPatientRequest {
    Doctor doctor;
    Patient patient;
    AppointmentType appointmentType;
}
