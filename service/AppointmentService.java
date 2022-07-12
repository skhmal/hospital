package com.khmal.hospital.service;

import com.khmal.hospital.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment addNewAppointment(Appointment appointment);

    List<Appointment> getPatientAppointments(Integer id);
}
