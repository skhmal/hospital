package com.khmal.hospital.dao.repository;

import com.khmal.hospital.dao.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> getPatientById(int id);
    Optional<Patient> findPatientById(int id);
    Optional<Patient> findPatientByUsername(String username);
}