package com.khmal.hospital.service;

import com.khmal.hospital.entity.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor addDoctor(Doctor doctor);
    Doctor getDoctorById(Integer id);
//    Doctor getDoctorByFirstName(String name);

    List<Doctor> getAllDoctors();
}
