package com.khmal.hospital.service;

import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService{

    private DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor getDoctorById(Integer id) {
//        Doctor doctor = doctorRepository.getDoctorById(id);
        Optional<Doctor> doctor = doctorRepository.findById(id);

        if (doctor == null){
            throw new IllegalArgumentException("Doctor is null");
        }else {
            return doctor.get();
        }
    }

//    @Override
//    public Doctor getDoctorByFirstName(String name) {
//        Doctor doctor = doctorRepository.getDoctorByFirstName(name);
//        return doctor;
//    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
