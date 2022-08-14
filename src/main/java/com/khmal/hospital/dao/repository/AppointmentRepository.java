package com.khmal.hospital.dao.repository;

import com.khmal.hospital.dao.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Optional<List<Appointment>> findAppointmentByHospitalStaffId(int id);
    Optional<List<Appointment>> findAppointmentByPatientId(int id);

    Optional<Page<Appointment>> findAppointmentByPatientId(int id, Pageable pageable);
}