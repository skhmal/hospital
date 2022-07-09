package com.khmal.hospital.service;

import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.Patient;
import com.khmal.hospital.repository.AdministratorRepository;
import com.khmal.hospital.repository.DoctorRepository;
import com.khmal.hospital.repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdministratorServiceImpl implements AdministratorService{

    private AdministratorRepository administratorRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;

    @NonNull
    public AdministratorServiceImpl(AdministratorRepository administratorRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.administratorRepository = administratorRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @NonNull
    public AdministratorServiceImpl() {
    }

    @Override
    public void addPatient(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    public void addDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }
}
