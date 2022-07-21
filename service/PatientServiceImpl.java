package com.khmal.hospital.service;


import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.entity.Appointment;
import com.khmal.hospital.entity.Patient;
import com.khmal.hospital.exception_handling.NoSuchUserException;
import com.khmal.hospital.mapper.PatientMapper;
import com.khmal.hospital.repository.AppointmentRepository;
import com.khmal.hospital.repository.PatientRepository;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.Mapper;
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
    public List<PatientDto> getAllPatients() {
        return PatientMapper.INSTANCE.toDto(patientRepository.findAll());
    }

    @Override
    public Patient getPatientByName(String name) {
        return null;
    }

    @Override
    public void addNewPatient(PatientDto patientDto) {
        try {
            Patient patient = PatientMapper.INSTANCE.toEntity(patientDto);
            patientRepository.save(patient);
        } catch (Exception e) {
            throw new NoSuchUserException("Patient wasn't saved");
        }
    }

    @Override
    public PatientDto updatePatient(PatientDto patientDto) {

        try {
            Patient patient = PatientMapper.INSTANCE.toEntity(patientDto);
            patientRepository.save(patient);
        } catch (Exception e) {
            throw new NoSuchUserException("Patient wasn't saved");
        }
        return patientDto;
    }

    @Override
    public void deletePatient(PatientDto patientDto) {

        try {
            patientRepository.delete(PatientMapper.INSTANCE.toEntity(patientDto));
        } catch (Exception e) {
            throw new NoSuchUserException("Patient wasn't delete");
        }
    }


}
