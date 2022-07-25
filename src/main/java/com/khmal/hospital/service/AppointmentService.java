package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.HospitalStuffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dto.AppointmentDto;

import com.khmal.hospital.dto.mapper.AppointmentMapper;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service

public class AppointmentService {

    private final PatientRepository patientRepository;
    private final HospitalStuffRepository hospitalStuffRepository;
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(PatientRepository patientRepository, HospitalStuffRepository hospitalStuffRepository, AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.hospitalStuffRepository = hospitalStuffRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public AppointmentDto createAppointment(Integer patientId, Integer hospitalStuffId, String appointmentType,
                                            String appointmentSummary) {
        Appointment appointment = new Appointment(
                LocalDate.now(),
                appointmentType,
                appointmentSummary,
                patientRepository.getPatientById(patientId),
                hospitalStuffRepository.getHospitalStuffById(hospitalStuffId));

        try {
            appointmentRepository.save(appointment);
        } catch (Exception e) {
            throw new NoSuchUserException("Appointment is not created");
        }
        return AppointmentMapper.INSTANCE.toDto(appointment);
    }
}
