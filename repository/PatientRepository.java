package com.khmal.hospital.repository;

import com.khmal.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient getPatientById(Integer id);
    List<Patient>  getAllByUserPatientNotNull();
}