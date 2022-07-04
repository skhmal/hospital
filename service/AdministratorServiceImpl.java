package com.khmal.Hosp.service;

import com.khmal.Hosp.entity.Patient;
import com.khmal.Hosp.repository.AdministratorRepository;
import com.khmal.Hosp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AdministratorServiceImpl implements AdministratorService{
    private AdministratorRepository administratorRepository;
    private PatientRepository patientRepository;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository administratorRepository, PatientRepository patientRepository) {
        this.administratorRepository = administratorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public void addPatient(Patient patient) {
       patientRepository.save(patient);
    }
}
