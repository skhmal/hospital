package com.khmal.hospital.repository;

import com.khmal.hospital.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> getAppointmentsByPatientId(Integer id);

    boolean deleteAppointmentById(Integer id);

    void deleteById(Integer id);

}