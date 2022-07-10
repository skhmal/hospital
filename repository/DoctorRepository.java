package com.khmal.hospital.repository;

import com.khmal.hospital.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor getDoctorById(Integer id);
//    Doctor getDoctorByFirstName(String firstname);
}