package com.khmal.hospital.dao.repository;

import com.khmal.hospital.dao.entity.Diagnose;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnoseRepository extends JpaRepository<Diagnose, Integer> {
}
