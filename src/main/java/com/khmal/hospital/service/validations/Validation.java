package com.khmal.hospital.service.validations;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.HospitalStuffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class Validation {

    private final HospitalStuffRepository hospitalStuffRepository;
    private final PatientRepository patientRepository;

    private final AppointmentRepository appointmentRepository;

    public Validation(HospitalStuffRepository hospitalStuffRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository) {
        this.hospitalStuffRepository = hospitalStuffRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public boolean checkHospitalStuffId(int id) {
        return hospitalStuffRepository.findHospitalStuffById(id).isPresent();
    }

    public boolean checkPatientId(int id) {
        return patientRepository.findPatientById(id).isPresent();
    }

    public boolean checkAppointmentDateForHospitalStuff(int patientId, int hospitalStuffId, LocalDateTime appointmentDateTime) {
        List<Appointment> hospitalStuffAppointmentList = appointmentRepository.findAppointmentByHospitalStuffId(hospitalStuffId);
        List<Appointment> patientAppointmentList = appointmentRepository.findAppointmentByPatientId(patientId);

        byte doctorAndPatientAreAvailable = 0;

        for (Appointment appointment : hospitalStuffAppointmentList
        ) {
            if (appointment.getDate().isEqual(appointmentDateTime)) {
                doctorAndPatientAreAvailable++;
            }
        }

        for (Appointment appointment : patientAppointmentList
        ) {
            if (appointment.getDate().isEqual(appointmentDateTime)) {
                doctorAndPatientAreAvailable++;
            }
        }
        return doctorAndPatientAreAvailable == 0 ;
    }
}
