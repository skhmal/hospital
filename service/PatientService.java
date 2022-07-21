package com.khmal.hospital.service;

import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.entity.Appointment;
import com.khmal.hospital.entity.Patient;

import java.util.List;


public interface PatientService {

    Patient getPatientById(Integer id);
    List<Appointment> getAllAppoitmentByPatientId(Integer patientId);

    List<PatientDto> getAllPatients();

    Patient getPatientByName(String name);

    void addNewPatient(PatientDto patient);

    PatientDto updatePatient(PatientDto patientDto);

    void deletePatient(PatientDto patientDto);
}
