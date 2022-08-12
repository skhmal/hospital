package com.khmal.hospital.dao.repository;

import com.khmal.hospital.dao.entity.HospitalStaff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalStaffRepository extends JpaRepository<HospitalStaff, Integer> {
    Optional<List<HospitalStaff>> getHospitalStuffByDoctorSpecializationIsNotNull();

    Optional<HospitalStaff> getHospitalStuffById(int id);

    Optional<HospitalStaff> findHospitalStuffById(int id);

    Optional<HospitalStaff> findHospitalStuffByUsername(String username);

    Page<HospitalStaff> getHospitalStaffByDoctorSpecializationIsNotNull(Pageable pageable);
}
