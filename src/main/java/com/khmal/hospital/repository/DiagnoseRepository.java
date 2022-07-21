package com.khmal.hospital.repository;

import com.khmal.hospital.entity.Diagnose;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnoseRepository extends JpaRepository<Diagnose, Integer> {
}
