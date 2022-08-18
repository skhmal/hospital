package com.khmal.hospital.service.validator;

import com.khmal.hospital.controller.exception.handling.IncorrectDataException;
import com.khmal.hospital.controller.exception.handling.NoSuchUserException;
import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.HospitalStaffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dao.repository.StaffRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Util class with validations methods for input data.
 *
 */
@Service
public class Validation {

    private static final Logger logger = LoggerFactory.getLogger(Validation.class);
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

    /**
     * Method which check Employee id in database.
     * @param id Employee id
     * @return true if id already exist in database
     */
    public boolean checkHospitalStaffId(int id) {
        if (hospitalStaffRepository.findHospitalStuffById(id).isEmpty()) {
            logger.error("checkHospitalStaffId error. Hospital staff with id {} is not found", id);
            throw new NoSuchUserException("Employee is not found");
        }
        return true;
    }

    /**
     * Method which check Patient id in database.
     * @param id Patient id
     * @return true if id already exist in database
     */
    public boolean checkPatientId(int id) {
        if (patientRepository.findPatientById(id).isEmpty()) {
            logger.error("checkPatientId error. Patient with id {} is not found", id);
            throw new NoSuchUserException("Patient is not found");
        }
        return true;
    }

    /**
     * Check medic and patient appointment dates to prevent overbooking
     * @param patientId patient id
     * @param hospitalStaffId medic id
     * @param appointmentDateTime Appointment date and time
     * @return true if both don't have appointments that time
     */
    public boolean checkAppointmentDateForHospitalStaff(int patientId, int hospitalStaffId, LocalDateTime appointmentDateTime) {

        if (appointmentRepository.findAppointmentByHospitalStaffId(hospitalStaffId).isPresent()) {
            List<Appointment> hospitalStuffAppointmentList =
                    appointmentRepository.findAppointmentByHospitalStaffId(hospitalStaffId).get();

            for (Appointment appointment : hospitalStuffAppointmentList
            ) {
                if (appointment.getDate().isEqual(appointmentDateTime)) {
                    logger.warn("checkAppointmentDateForHospitalStaff warn. Doctor with id {} is busy", hospitalStaffId);
                    throw new IncorrectDataException("The doctor is busy at that moment");
                }
            }
        }

        if (appointmentRepository.findAppointmentByPatientId(patientId).isPresent()) {
            List<Appointment> patientAppointmentList =
                    appointmentRepository.findAppointmentByPatientId(patientId).get();

            for (Appointment appointment : patientAppointmentList
            ) {
                if (appointment.getDate().isEqual(appointmentDateTime)) {
                    logger.warn("checkAppointmentDateForHospitalStaff warn. Patient with id {} is busy", patientId);
                    throw new IncorrectDataException("The patient is busy at that moment");
                }
            }
        }
        return true;
    }

    /**
     * Check role in database.
     * @param roleId role id
     * @return true if role already exist in database
     */
    public boolean checkStaffRoleInDataBase(int roleId) {
        if (staffRoleRepository.getStuffRoleById(roleId).isEmpty()) {
            logger.warn("checkStaffRoleInDataBase warn. StaffRole with id {} is not found", roleId);
            throw new IncorrectDataException("Role with id " + roleId + " not found");
        }
        return true;
    }

    /**
     * Check doctor specialization
     * @param doctorSpecialization
     * @return return true if doctor specialization already exist or equals null(administrator and nurse have null
     * in field doctor specialization)
     */
    public boolean checkDoctorSpecialization(String doctorSpecialization) {
        boolean checkResult = Stream.of(HospitalStaff.DoctorSpecialization.values())
                .anyMatch(s -> s.name().equals(doctorSpecialization));

        if (doctorSpecialization == null) {
            logger.info("checkDoctorSpecialization: doctor specialization = null");
            return true;
        }

        if (!checkResult) {
            logger.warn("checkDoctorSpecialization warn. Doctor specialization with name {} is not found",
                    doctorSpecialization);
            throw new IncorrectDataException("Doctor specialization is incorrect or not chosen");
        } else {
            return true;
        }
    }

    /**
     * Check appoint doctor-patient to prevent double appoint.
     * @param doctorId doctor id
     * @param patientId patient id
     * @return true if relation doesn't exist
     */
    public boolean checkDoubleAppoint(int doctorId, int patientId) {
        HospitalStaff doctor = hospitalStaffRepository.findHospitalStuffById(doctorId).orElseThrow(
                () -> new NoSuchUserException("Doctor with id = " + doctorId + " is not found"));
        List<Patient> patientList = doctor.getPatientsList();

        for (Patient patient : patientList
        ) {
            if (patient.getId() == patientId) {
                logger.warn("checkDoubleAppoint warning. Appoint already exist");
                return false;
            }
        }
        return true;
    }

    /**
     * Check appointment type for medic to prevent provide incorrect appointment type.
     * @param name appointment name
     * @return true if appointment already exist
     */
    public boolean checkAppointmentType(String name) {
        if (Arrays.stream(Appointment.DoctorAppointment.values()).anyMatch(x -> x.name().equals(name))
                || Arrays.stream(Appointment.NurseAppointment.values()).anyMatch(y -> y.name().equals(name))) {
            return true;
        } else {
            logger.warn("checkAppointmentType error {}", name);
            throw new IncorrectDataException("Appointment type can't be empty");
        }
    }
}
