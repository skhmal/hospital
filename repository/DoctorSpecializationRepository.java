package com.khmal.hospital.repository;

import com.khmal.hospital.entity.DoctorSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorSpecializationRepository extends JpaRepository<DoctorSpecialization, IncompatibleClassChangeError> {
   DoctorSpecialization getDoctorSpecializationById(Integer id);
}
