package com.khmal.hospital.dao.repository;

import com.khmal.hospital.dao.entity.HospitalStuff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HospitalStuffRepository extends JpaRepository<HospitalStuff, Integer> {
    Optional<List<HospitalStuff>> getHospitalStuffByDoctorSpecializationIsNotNull();

    Optional<HospitalStuff> getHospitalStuffById(int id);

    Optional<HospitalStuff> findHospitalStuffById(int id);

    Optional<HospitalStuff> findHospitalStuffByUsername(String username);



    Page<HospitalStuff> getHospitalStuffByDoctorSpecializationIsNotNull(Pageable pageable);
}
