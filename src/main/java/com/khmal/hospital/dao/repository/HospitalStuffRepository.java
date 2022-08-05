package com.khmal.hospital.dao.repository;

import com.khmal.hospital.dao.entity.HospitalStuff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalStuffRepository extends JpaRepository<HospitalStuff, Integer> {
    Optional<List<HospitalStuff>> getHospitalStuffByDoctorSpecializationIsNotNull();

    Optional<HospitalStuff> getHospitalStuffById(int id);

    Optional<HospitalStuff> findHospitalStuffById(int id);

    Optional<HospitalStuff> findHospitalStuffByUsername(String username);
}
