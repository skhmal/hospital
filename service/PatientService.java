package com.khmal.hospital.service;

import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.entity.Appointment;
import com.khmal.hospital.entity.Patient;

import java.util.List;


public interface PatientService {

    Patient getPatientById(Integer id);
    List<Appointment> getAllAppoitmentByPatientId(Integer patientId);

    List<Patient> getAllPatients();

    Patient getPatientByName(String name);

    void savePatient(PatientDto patient);

    void deletePatient(Patient patient);
}
