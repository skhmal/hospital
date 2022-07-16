package com.khmal.hospital.service;

import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.DoctorSpecialization;
import com.khmal.hospital.entity.Patient;
import com.khmal.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor getDoctorById(Integer id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.orElse(null);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public void deleteDoctor(Doctor doctor) {
        doctorRepository.delete(doctor);
    }

    @Override
    public List<Doctor> getDoctorsBySpecialization(DoctorSpecialization doctorSpecialization) {
        List<Doctor> doctorList = doctorRepository.findDoctorsByDoctorSpecialization(doctorSpecialization);
        return doctorList;
    }

    @Override
    public Integer getPatientCounter(List<Patient> patientList) {
//        Integer quantity = doctorRepository.countByPatientsList(patientList);
        return null;
    }
}
