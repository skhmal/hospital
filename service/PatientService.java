package com.khmal.hospital.service;

import com.khmal.hospital.entity.Appointment;
import com.khmal.hospital.entity.Patient;

import java.util.List;

public interface PatientService {

    Patient getPatientById(Integer id);
    List<Appointment> getAllAppoitmentsByUserId(Integer userId);

    List<Patient> getAllPatients();

    Patient getPatientByName(String name);
}
