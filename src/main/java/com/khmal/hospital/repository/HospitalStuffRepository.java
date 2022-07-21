package com.khmal.hospital.repository;

import com.khmal.hospital.entity.HospitalStuff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalStuffRepository extends JpaRepository<HospitalStuff, Integer> {
}
