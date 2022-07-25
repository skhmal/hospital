package com.khmal.hospital.dao.repository;

import com.khmal.hospital.dao.entity.HospitalStuff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalStuffRepository extends JpaRepository<HospitalStuff, Integer> {
    List<HospitalStuff> getHospitalStuffByDoctorSpecializationIsNotNull();

    HospitalStuff getHospitalStuffById(int id);
}
