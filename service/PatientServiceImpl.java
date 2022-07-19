package com.khmal.hospital.service;


import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.entity.Appointment;
import com.khmal.hospital.entity.Patient;
import com.khmal.hospital.entity.Role;
import com.khmal.hospital.exception_handling.NoSuchUserException;
import com.khmal.hospital.mapper.PatientMapper;
import com.khmal.hospital.repository.AppointmentRepository;
import com.khmal.hospital.repository.PatientRepository;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Log4j2
public class PatientServiceImpl implements PatientService{

    private PatientRepository patientRepository;
    private AppointmentRepository appointmentRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public Patient getPatientById(Integer id) {
       Patient patient = new Patient();
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
    public void savePatient(PatientDto patientDto) {
        try {
            Patient patient = PatientMapper.INSTANCE.toEntity(patientDto);
            System.out.println(patient);
            patientRepository.save(patient);
        } catch (Exception e) {
            throw new NoSuchUserException("Patient not saved");
        }
    }

    @Override
    public void deletePatient(Patient patient) {
        patientRepository.delete(patient);
    }


}
