package com.khmal.hospital.repository;

import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.DoctorSpecialization;
import com.khmal.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor getDoctorById(Integer id);
    List<Doctor> findDoctorsByDoctorSpecialization(DoctorSpecialization doctorSpecialization);

//    Integer countByPatientsList(List<Patient> patientsList);
}