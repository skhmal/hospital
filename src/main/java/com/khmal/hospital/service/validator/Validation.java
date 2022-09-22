package com.khmal.hospital.service.validator;

import com.khmal.hospital.controller.exception.handling.IncorrectDataException;
import com.khmal.hospital.controller.exception.handling.NoSuchUserException;
import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.entity.StaffRole;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.HospitalStaffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dao.repository.StaffRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Util class with validations methods for input data.
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
     *
     * @param id Employee id
     * @return true if id already exist in database
     */
    public HospitalStaff checkHospitalStaffId(int id) {
        Optional<HospitalStaff> hospitalStaff = hospitalStaffRepository.findHospitalStuffById(id);

        if (hospitalStaff.isEmpty()) {

            logger.error("checkHospitalStaffId error. Hospital staff with id {} is not found", id);

            throw new NoSuchUserException("Employee is not found");
        } else {
            return hospitalStaff.get();
        }
    }

    /**
     * Method which check Patient id in database.
     *
     * @param id Patient id
     * @return true if id already exist in database
     */
    public Patient checkPatientId(int id) {
        Optional<Patient> patient = patientRepository.findPatientById(id);

        if (patient.isEmpty()) {
            logger.error("checkPatientId error. Patient with id {} is not found", id);

            throw new NoSuchUserException("Patient with ID = " + id + " is not found");
        } else {
            return patient.get();
        }
    }

    /**
     * Check medic and patient appointment dates to prevent overbooking
     *
     * @param patientId           patient id
     * @param hospitalStaffId     medic id
     * @param appointmentDateTime Appointment date and time
     * @return true if both don't have appointments that time
     */
    public boolean checkAppointmentDateForHospitalStaff(int patientId, int hospitalStaffId, LocalDateTime appointmentDateTime) {
        Optional<List<Appointment>> hospitalStuffAppointmentList = appointmentRepository.findAppointmentByHospitalStaffId(hospitalStaffId);

        if (hospitalStuffAppointmentList.isPresent()) {

            for (Appointment appointment : hospitalStuffAppointmentList.get()
            ) {
                if (appointment.getDate().isEqual(appointmentDateTime)) {

                    logger.warn("checkAppointmentDateForHospitalStaff warn. Doctor with id {} is busy", hospitalStaffId);

                    throw new IncorrectDataException("The doctor is busy at that moment");
                }
            }
        }

        Optional<List<Appointment>> patientAppointmentList = appointmentRepository.findAppointmentByPatientId(patientId);

        if (patientAppointmentList.isPresent()) {

            for (Appointment appointment : patientAppointmentList.get()
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
     *
     * @param roleId role id
     * @return true if role already exist in database
     */
    public StaffRole checkStaffRoleInDataBase(int roleId) {
        Optional<StaffRole> staffRole = staffRoleRepository.getStuffRoleById(roleId);

        if (staffRole.isEmpty()) {
            logger.warn("checkStaffRoleInDataBase warn. StaffRole with id {} is not found", roleId);

            throw new IncorrectDataException("Role with id " + roleId + " not found");

        } else {
            return staffRole.get();
        }
    }

    /**
     * Check doctor specialization
     *
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
     *
     * @param doctorId  doctor id
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
     *
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

    public void checkBirthdayDate(LocalDate birthday) {
        if (birthday.isAfter(LocalDate.now())) {
            throw new IncorrectDataException("Birthday date is wrong");
        }
    }

    public void checkAppointmentDate(LocalDateTime appointmentDateTime) {
        int firstWorkingHour = 8;
        int lastWorkingHour = 20;
        DayOfWeek dayOff = DayOfWeek.SUNDAY;

        if (appointmentDateTime.isBefore(LocalDateTime.now()) || appointmentDateTime.getHour() > lastWorkingHour
                || appointmentDateTime.getHour() < firstWorkingHour || appointmentDateTime.getDayOfWeek().equals(dayOff)) {
            throw new IncorrectDataException("Wrong appointment date or time");
        }
    }
}
