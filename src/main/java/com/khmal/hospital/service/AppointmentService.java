package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.HospitalStuffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dto.AppointmentDto;

import com.khmal.hospital.dto.mapper.AppointmentMapper;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import com.khmal.hospital.service.validations.Validation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class AppointmentService {

    private final PatientRepository patientRepository;
    private final HospitalStuffRepository hospitalStuffRepository;
    private final AppointmentRepository appointmentRepository;
    private final Validation validation;

    public AppointmentService(PatientRepository patientRepository, HospitalStuffRepository hospitalStuffRepository, AppointmentRepository appointmentRepository, Validation validation) {
        this.patientRepository = patientRepository;
        this.hospitalStuffRepository = hospitalStuffRepository;
        this.appointmentRepository = appointmentRepository;
        this.validation = validation;
    }

    public AppointmentDto createAppointment(Integer patientId, Integer hospitalStuffId, String appointmentType,
                                            String appointmentSummary, LocalDateTime appointmentDate) {

        if (!(validation.checkPatientId(patientId) || validation.checkHospitalStuffId(hospitalStuffId))){
            throw new NoSuchUserException("One of user not found");
        }

        Appointment appointment;

        if (validation.checkAppointmentDateForHospitalStuff(patientId, hospitalStuffId,appointmentDate)){
             appointment = new Appointment(
                    appointmentDate,
                    appointmentType,
                    appointmentSummary,
                    patientRepository.getPatientById(patientId),
                    hospitalStuffRepository.getHospitalStuffById(hospitalStuffId));

            appointmentRepository.save(appointment);

        }else {
            throw new NoSuchUserException("One or both of the participants is not available at the selected time");
        }

        return AppointmentMapper.INSTANCE.toDto(appointment);
    }

    public List<Patient> getPatientList(int id) {
        return hospitalStuffRepository.getHospitalStuffById(id).getPatientsList();
    }
}
