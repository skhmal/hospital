package com.khmal.hospital.service.validator;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.HospitalStaffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dao.repository.StaffRoleRepository;
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class Validation {

    private final HospitalStaffRepository hospitalStaffRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final StaffRoleRepository staffRoleRepository;

    public Validation(HospitalStaffRepository hospitalStaffRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository, StaffRoleRepository staffRoleRepository) {
        this.hospitalStaffRepository = hospitalStaffRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.staffRoleRepository = staffRoleRepository;
    }

    public boolean checkHospitalStuffId(int id) {
        if (hospitalStaffRepository.findHospitalStuffById(id).isEmpty()) {
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

        if (appointmentRepository.findAppointmentByHospitalStaffId(hospitalStuffId).isPresent()) {
            List<Appointment> hospitalStuffAppointmentList =
                    appointmentRepository.findAppointmentByHospitalStaffId(hospitalStuffId).get();

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

    public boolean checkStuffRoleInDataBase(int roleId) {
        if (staffRoleRepository.getStuffRoleById(roleId).isEmpty()) {
            throw new IncorrectDateException("Role with id " + roleId + " not found");
        }
        return true;
    }

    public boolean checkDoctorSpecialization(String doctorSpecialization) {
        boolean checkResult = Stream.of(HospitalStaff.DoctorSpecialization.values())
                .anyMatch(s -> s.name().equals(doctorSpecialization));

        if(doctorSpecialization == null){
            return true;
        }

        if (!checkResult) {
            throw new IncorrectDateException("Doctor specialization is incorrect or not chosen");
        } else {
            return true;
        }
    }

    public boolean checkDoubleAppoint(int doctorId, int patientId) {
        HospitalStaff doctor = hospitalStaffRepository.findHospitalStuffById(doctorId).orElseThrow(
                () -> new NoSuchUserException("Doctor with id = " + doctorId + " is not found"));
        List<Patient> patientList = doctor.getPatientsList();

        for (Patient patient : patientList
        ) {
            if (patient.getId() == patientId) {
                return false;
            }
        }
        return true;
    }

    public boolean checkAppointmentType(String name) {
        if (Arrays.stream(Appointment.DoctorAppointment.values()).anyMatch(x -> x.name().equals(name))
                || Arrays.stream(Appointment.NurseAppointment.values()).anyMatch(y -> y.name().equals(name))) {
            return true;
        } else {
            throw new IncorrectDateException("Appointment type can't be empty");
        }
    }


}
