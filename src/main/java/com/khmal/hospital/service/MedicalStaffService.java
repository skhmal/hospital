package com.khmal.hospital.service;

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
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
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
     * @return
     */
    public AppointmentDto createAppointment(int patientId,
                                            @NotNull(message = "Employee can't be empty") int hospitalStaffId,
                                            @NotBlank(message = "Appointment type can't be empty") String appointmentType,
                                            @NotBlank(message = "Summary can't be empty") String appointmentSummary,
                                            @NotNull(message = "Date can't be empty") LocalDateTime appointmentDate) {
        Appointment appointment = null;
        logger.info("Method createAppointment started");
        validation.checkAppointmentType(appointmentType);

        if (validation.checkHospitalStaffId(hospitalStaffId) && validation.checkPatientId(patientId) &&
                validation.checkAppointmentDateForHospitalStaff(patientId, hospitalStaffId, appointmentDate)) {

            appointment = new Appointment(
                    appointmentDate,
                    appointmentType,
                    appointmentSummary,
                    patientRepository.getPatientById(patientId).get(),
                    hospitalStaffRepository.getHospitalStuffById(hospitalStaffId).get());
            appointmentRepository.save(appointment);
        }
        logger.info("Method createAppointment stopped");

        return AppointmentMapper.INSTANCE.toDto(appointment);
    }

    /**
     * Method create diagnose (only for doctor with assigned patient).
     * - Check patient and doctor id's in database
     * - Check doctor list(patient): if he has only 1 doctor - discharge
     * - Delete patient with diagnose from patient list.
     *
     * @param patientId patient id
     * @param doctorId doctor id
     * @param summary diagnose summary
     * @return DiagnoseDto
     */
    @Transactional
    public DiagnoseDto createDiagnose(@NotNull(message = "Patient can't be empty") int patientId,
                                      @NotNull(message = "Employee can't be empty") int doctorId,
                                      @NotBlank(message = "Summary can't be empty") String summary) {
        Diagnose diagnose = null;
        logger.info("Method createDiagnose started");
        if (validation.checkHospitalStaffId(doctorId) && validation.checkPatientId(patientId)) {

            Patient patient = patientRepository.getPatientById(patientId).get();
            HospitalStaff doctor = hospitalStaffRepository.getHospitalStuffById(doctorId).get();

            diagnose = new Diagnose(
                    summary,
                    LocalDate.now(),
                    patient,
                    doctor);

            if (patient.getDoctorsList().size() == 1) {
                logger.info("Patient discharged");
                patient.setDischarged(true);
                patientRepository.save(patient);
            }

            logger.info("Method createDiagnose stopped");

            doctor.getPatientsList().remove(patient);
            doctor.setPatientCount(doctor.getPatientCount() - 1);
        }
        diagnoseRepository.save(diagnose);

        return DiagnoseMapper.INSTANCE.toDto(diagnose);
    }

    /**
     * Method getDoctorPatients return all assigned patients.
     * @param doctorId doctor id
     * @return List<PatientDto></PatientDto>
     */
    public List<PatientDto> getDoctorPatients(int doctorId) {

        validation.checkHospitalStaffId(doctorId);

        if (!hospitalStaffRepository.getHospitalStuffById(doctorId).get().getPatientsList().isEmpty()) {

            HospitalStaff doctor = hospitalStaffRepository.getHospitalStuffById(doctorId).get();

            return PatientMapper.INSTANCE.toDto(doctor.getPatientsList());
        } else {
            logger.warn("getDoctorPatients warn. Doctor doesn't have a patients");
            throw new IncorrectDateException("Doctor doesn't have a patients for appointment/diagnose");
        }
    }
}
