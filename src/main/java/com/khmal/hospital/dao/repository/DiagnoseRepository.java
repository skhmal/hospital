package com.khmal.hospital.dao.repository;

import com.khmal.hospital.dao.entity.Diagnose;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiagnoseRepository extends JpaRepository<Diagnose, Integer> {

    Optional<List<Diagnose>> getDiagnoseByPatientId(int id);

    Page<Diagnose> findDiagnoseByPatientId(int id, Pageable pageable);
}
