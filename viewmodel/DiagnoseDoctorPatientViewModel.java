package com.khmal.hospital.viewmodel;

import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.Patient;
import lombok.Getter;

@Getter
public class DiagnoseDoctorPatientViewModel {
    Patient patient;
    Doctor doctor;
    String summary;

}
