package com.khmal.hospital.request;

import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.Patient;
import lombok.Getter;

@Getter
public class DiagnoseDoctorPatientRequest {
    Patient patient;
    Doctor doctor;
    String summary;

}
