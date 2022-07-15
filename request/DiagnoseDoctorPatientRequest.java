package com.khmal.hospital.request;

import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.Patient;
import lombok.Getter;

@Getter
public class DiagnoseDoctorPatientRequest {
    private Patient patient;
    private Doctor doctor;
    private String summary;

}
