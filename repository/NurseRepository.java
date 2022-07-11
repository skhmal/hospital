package com.khmal.hospital.repository;

import com.khmal.hospital.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NurseRepository extends JpaRepository<Nurse, Integer> {
}
