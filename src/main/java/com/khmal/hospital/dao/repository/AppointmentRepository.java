package com.khmal.hospital.dao.repository;

import com.khmal.hospital.dao.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAppointmentByHospitalStuffId(int id);
    List<Appointment> findAppointmentByPatientId(int id);
}