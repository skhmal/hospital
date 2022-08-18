package com.khmal.hospital.service;

import com.khmal.hospital.controller.exception.handling.IncorrectDataException;
import com.khmal.hospital.controller.exception.handling.NoSuchUserException;
import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.Diagnose;
import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.DiagnoseRepository;
import com.khmal.hospital.dao.repository.HospitalStaffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.DiagnoseDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.mapper.AppointmentMapper;
import com.khmal.hospital.mapper.DiagnoseMapper;
import com.khmal.hospital.mapper.PatientMapper;
import com.khmal.hospital.service.validator.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for medical staff (doctor and nurse).
 * 1) createAppointment - Create appointment for both medical roles.
 * 2) createDiagnose- Create diagnose (only for doctor).
 * 3) getDoctorPatients - Get all patients for doctor. Doctor can create appointment and diagnoses only for assigned patients.
 */
@Service
@Validated
public class MedicalStaffService {
    private static final Logger logger = LoggerFactory.getLogger(MedicalStaffService.class);
    private final PatientRepository patientRepository;
    private final HospitalStaffRepository hospitalStaffRepository;
    private final AppointmentRepository appointmentRepository;
    private final Validation validation;
    private final DiagnoseRepository diagnoseRepository;
    private static final String NOT_FOUND = " is not found";

    public MedicalStaffService(PatientRepository patientRepository, HospitalStaffRepository hospitalStaffRepository, AppointmentRepository appointmentRepository, Validation validation, DiagnoseRepository diagnoseRepository) {
        this.patientRepository = patientRepository;
        this.hospitalStaffRepository = hospitalStaffRepository;
        this.appointmentRepository = appointmentRepository;
        this.validation = validation;
        this.diagnoseRepository = diagnoseRepository;
    }

    /**
     * Method createAppointment for role: nurse, doctor.
     *
     * @param patientId          patient id
     * @param hospitalStaffId    medic id
     * @param appointmentType    appointment type
     * @param appointmentSummary summary
     * @param appointmentDate    appointment date
     *                           Check appointment type to prevent provide incorrect data.
     *                           Check medic and patient id in database.
     *                           Check date to prevent overbooking
     * @return AppointmentDto
     */
    public AppointmentDto createAppointment(@NotNull(message = "Pastient can't be empty") Integer patientId,
                                            @NotNull(message = "Employee can't be empty") Integer hospitalStaffId,
                                            @NotBlank(message = "Appointment type can't be empty") String appointmentType,
                                            @NotBlank(message = "Summary can't be empty") String appointmentSummary,
                                            @NotNull(message = "Date can't be empty") LocalDateTime appointmentDate) {
        Appointment appointment = null;

        logger.info("Method createAppointment started. Creating appointment for medic id = {}, patient id = {}" +
                ", date = {}", hospitalStaffId, patientId, appointmentDate);

        validation.checkAppointmentType(appointmentType);

        if (validation.checkHospitalStaffId(hospitalStaffId) && validation.checkPatientId(patientId) &&
                validation.checkAppointmentDateForHospitalStaff(patientId, hospitalStaffId, appointmentDate)) {

            Patient patient = patientRepository.getPatientById(patientId).orElseThrow(
                    () -> new NoSuchUserException("Patient with id = " + patientId + NOT_FOUND)
            );
            HospitalStaff doctor = hospitalStaffRepository.getHospitalStuffById(hospitalStaffId).orElseThrow(
                    () -> new NoSuchUserException("Medic with id = " + hospitalStaffId + NOT_FOUND));

            appointment = new Appointment(
                    appointmentDate,
                    appointmentType,
                    appointmentSummary,
                    patient,
                    doctor);

           int appointmentId =  appointmentRepository.save(appointment).getId();

           logger.info("Method createAppointment finished. Appointment id = {} has been created", appointmentId);
        }


        return AppointmentMapper.INSTANCE.toDto(appointment);
    }

    /**
     * Method create diagnose (only for doctor with assigned patient).
     * - Check patient and doctor id's in database
     * - Check doctor list(patient): if he has only 1 doctor - discharge
     * - Delete patient with diagnose from patient list.
     *
     * @param patientId patient id
     * @param doctorId  doctor id
     * @param summary   diagnose summary
     * @return DiagnoseDto
     */
    @Transactional
    public DiagnoseDto createDiagnose(@NotNull(message = "Patient can't be empty") int patientId,
                                      @NotNull(message = "Employee can't be empty") int doctorId,
                                      @NotBlank(message = "Summary can't be empty") String summary) {
        Diagnose diagnose = null;

        logger.info("Method createDiagnose started. Creating diagnose for patient id = {}, doctor id = {}", patientId,
                doctorId);

        if (validation.checkHospitalStaffId(doctorId) && validation.checkPatientId(patientId)) {

            Patient patient = patientRepository.getPatientById(patientId).orElseThrow(
                    () -> new NoSuchUserException("Patient with id = " + patientId + NOT_FOUND)
            );
            HospitalStaff doctor = hospitalStaffRepository.getHospitalStuffById(doctorId).orElseThrow(
                    () -> new NoSuchUserException("Doctor with id = " + doctorId + NOT_FOUND)
            );

            diagnose = new Diagnose(
                    summary,
                    LocalDate.now(),
                    patient,
                    doctor);

            if (patient.getDoctorsList().size() == 1) {

                logger.info("Patient id = {} discharged", patient.getId());

                patient.setDischarged(true);

                patientRepository.save(patient);
            }

            doctor.getPatientsList().remove(patient);
            doctor.setPatientCount(doctor.getPatientCount() - 1);

            int diagnoseId = diagnoseRepository.save(diagnose).getId();

            logger.info("Method createDiagnose finished. Diagnose with id {} has been created", diagnoseId);
        }
        return DiagnoseMapper.INSTANCE.toDto(diagnose);
    }

    /**
     * Method getDoctorPatients return all assigned patients.
     *
     * @param doctorId doctor id
     * @return List <PatientDto >
     */
    public List<PatientDto> getDoctorPatients(int doctorId) {

        logger.info("Method getDoctorPatients started. Get patient list for doctor id = {}", doctorId);

        validation.checkHospitalStaffId(doctorId);

        List<Patient> patientList = hospitalStaffRepository.getHospitalStuffById(doctorId)
                .orElseThrow(() -> new IncorrectDataException(
                        "Doctor id = " + doctorId + " doesn't have patients")).getPatientsList();

        if (!patientList.isEmpty()) {

            logger.info("Method getDoctorPatients finished");

            return PatientMapper.INSTANCE.toDto(patientList);

        } else {

            logger.warn("getDoctorPatients warn. Doctor id = {} doesn't have patients", doctorId);

            throw new IncorrectDataException("Doctor doesn't have a patients for appointment / diagnose");
        }
    }
}
