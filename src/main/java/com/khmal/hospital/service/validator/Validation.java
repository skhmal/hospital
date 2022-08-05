package com.khmal.hospital.service.validator;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.HospitalStuff;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.HospitalStuffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dao.repository.StuffRoleRepository;
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
public class Validation {

    private final HospitalStuffRepository hospitalStuffRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final StuffRoleRepository stuffRoleRepository;

    public Validation(HospitalStuffRepository hospitalStuffRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository, StuffRoleRepository stuffRoleRepository) {
        this.hospitalStuffRepository = hospitalStuffRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.stuffRoleRepository = stuffRoleRepository;
    }

    public boolean checkHospitalStuffId(int id) {
        if (hospitalStuffRepository.findHospitalStuffById(id).isEmpty()) {
            throw new NoSuchUserException("Employee is not found");
        }
        return true;
    }

    public boolean checkPatientId(int id) {
        if (patientRepository.findPatientById(id).isEmpty()) {
            throw new NoSuchUserException("Patient is not found");
        }
        return true;
    }

    public boolean checkAppointmentDateForHospitalStuff(int patientId, int hospitalStuffId, LocalDateTime appointmentDateTime) {

        if (appointmentRepository.findAppointmentByHospitalStuffId(hospitalStuffId).isPresent()) {
            List<Appointment> hospitalStuffAppointmentList =
                    appointmentRepository.findAppointmentByHospitalStuffId(hospitalStuffId).get();

            for (Appointment appointment : hospitalStuffAppointmentList
            ) {
                if (appointment.getDate().isEqual(appointmentDateTime)) {
                    throw new IncorrectDateException("The doctor is busy at that moment");
                }
            }
        }

        if (appointmentRepository.findAppointmentByPatientId(patientId).isPresent()) {
            List<Appointment> patientAppointmentList =
                    appointmentRepository.findAppointmentByPatientId(patientId).get();

            for (Appointment appointment : patientAppointmentList
            ) {
                if (appointment.getDate().isEqual(appointmentDateTime)) {
                    throw new IncorrectDateException("The patient is busy at that moment");
                }
            }
        }
        return true;
    }

    public boolean checkStuffRoleInDataBase(int roleId){
        if(stuffRoleRepository.getStuffRoleById(roleId).isEmpty()){
          throw   new IncorrectDateException("Role with id " + roleId + " not found");
        }
        return true;
    }

    public boolean checkDoctorSpecialization(String doctorSpecialization){
        boolean checkResult = Stream.of(HospitalStuff.DoctorSpecialization.values())
                .anyMatch(s -> s.name().equals(doctorSpecialization));

        if (!checkResult){
            throw new IncorrectDateException("Specialization " + doctorSpecialization + " is not found");
        }else {
            return true;
        }
    }
}
