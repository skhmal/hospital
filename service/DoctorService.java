package com.khmal.hospital.service;

import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.DoctorSpecialization;
import com.khmal.hospital.entity.Patient;

import java.util.List;

public interface DoctorService {
    Doctor saveDoctor(Doctor doctor);

    Doctor getDoctorById(Integer id);

    List<Doctor> getAllDoctors();

    void deleteDoctor(Doctor doctor);

    List<Doctor> getDoctorsBySpecialization(DoctorSpecialization doctorSpecialization);

    Integer getPatientCounter(List<Patient> patientList);
}
