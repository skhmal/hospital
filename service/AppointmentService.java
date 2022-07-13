package com.khmal.hospital.service;

import com.khmal.hospital.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment saveAppointment(Appointment appointment);

    void deleteById(Integer id);

    void deleteAppointment(Appointment appointment);

    List<Appointment> getPatientAppointments(Integer id);

    Appointment updateAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();

}
