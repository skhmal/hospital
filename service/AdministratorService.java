package com.khmal.hospital.service;


import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.Patient;

public interface AdministratorService {
    void addPatient(Patient patient);
    void addDoctor(Doctor doctor);
}
