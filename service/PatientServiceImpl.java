package com.khmal.hospital.service;


import com.khmal.hospital.entity.Appointment;
import com.khmal.hospital.entity.Patient;
import com.khmal.hospital.repository.AppointmentRepository;
import com.khmal.hospital.repository.DoctorRepository;
import com.khmal.hospital.repository.PatientRepository;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Log4j2
public class PatientServiceImpl implements PatientService{


    @NonNull
    private PatientRepository patientRepository;
    @NonNull
    private DoctorRepository doctorRepository;
    @NonNull
    private AppointmentRepository appointmentRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public Patient getPatientById(Integer id) {
       Patient patient = patientRepository.getPatientById(id);
        return patient;
    }

    @Override
    public List<Appointment> getAllAppoitmentByPatientId(Integer patientId) {
        return null;
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patientList = patientRepository.findAll();
        return patientList;
    }

    @Override
    public Patient getPatientByName(String name) {
        return null;
    }

    @Override
    public void savePatient(Patient patient) {
       patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Patient patient) {
        patientRepository.delete(patient);
    }


}
