package com.khmal.hospital.viewmodel;

import com.khmal.hospital.entity.AppointmentType;
import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.Patient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentDoctorPatient {
    Doctor doctor;
    Patient patient;
    AppointmentType appointmentType;
}
