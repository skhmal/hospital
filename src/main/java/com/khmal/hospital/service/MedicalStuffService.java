package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.Diagnose;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.DiagnoseRepository;
import com.khmal.hospital.dao.repository.HospitalStuffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.DiagnoseDto;
import com.khmal.hospital.dto.mapper.AppointmentMapper;
import com.khmal.hospital.dto.mapper.DiagnoseMapper;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import com.khmal.hospital.service.validator.Validation;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
public class MedicalStuffService {

    private final PatientRepository patientRepository;
    private final HospitalStuffRepository hospitalStuffRepository;
    private final AppointmentRepository appointmentRepository;
    private final Validation validation;
    private final DiagnoseRepository diagnoseRepository;

    public MedicalStuffService(PatientRepository patientRepository, HospitalStuffRepository hospitalStuffRepository, AppointmentRepository appointmentRepository, Validation validation, DiagnoseRepository diagnoseRepository) {
        this.patientRepository = patientRepository;
        this.hospitalStuffRepository = hospitalStuffRepository;
        this.appointmentRepository = appointmentRepository;
        this.validation = validation;
        this.diagnoseRepository = diagnoseRepository;
    }

    public AppointmentDto createAppointment(@NotNull(message = "Patient can't be empty") Integer patientId,
                                            @NotNull(message = "Employee can't be empty") Integer hospitalStuffId,
                                            @NotBlank(message = "Appointment type can't be empty") String appointmentType,
                                            @NotBlank(message = "Summary can't be empty") String appointmentSummary,
                                            @NotNull(message = "Date can't be empty") LocalDateTime appointmentDate) {
        Appointment appointment = null;

        if (validation.checkHospitalStuffId(hospitalStuffId) && validation.checkPatientId(patientId) &&
                validation.checkAppointmentDateForHospitalStuff(patientId, hospitalStuffId, appointmentDate)) {

            appointment = new Appointment(
                    appointmentDate,
                    appointmentType,
                    appointmentSummary,
                    patientRepository.getPatientById(patientId).orElseThrow(
                            () -> new NoSuchUserException("Patient is not found")
                    ),
                    hospitalStuffRepository.getHospitalStuffById(hospitalStuffId).orElseThrow(
                            () -> new NoSuchUserException("Employee is not found")
                    ));
        }

        return AppointmentMapper.INSTANCE.toDto(appointmentRepository.save(appointment));
    }

    public List<Patient> getDoctorsPatientListById(@NotNull(message = "Doctor id can't be empty") int id) {
        return hospitalStuffRepository.getHospitalStuffById(id).orElseThrow(
                () -> new NoSuchUserException("No patient in patient list")
        ).getPatientsList();
    }

    public DiagnoseDto createDiagnose(@NotNull(message = "Patient can't be empty") int patientId,
                                      @NotNull(message = "Employee can't be empty") int doctorId,
                                      @NotBlank(message = "Summary can't be empty") String summary) {
        Diagnose diagnose = null;

        if (validation.checkHospitalStuffId(doctorId) && validation.checkPatientId(patientId)) {
            diagnose = new Diagnose(
                    summary,
                    LocalDate.now(),
                    patientRepository.getPatientById(patientId).orElseThrow(
                            () -> new NoSuchUserException("Patient is not found")
                    ),
                    hospitalStuffRepository.getHospitalStuffById(doctorId).orElseThrow(
                            () -> new NoSuchUserException("Doctor is not found")
                    ));
        }

        return DiagnoseMapper.INSTANCE.toDto(diagnoseRepository.save(diagnose));
    }

    public List<DiagnoseDto> getPatientDiagnoses(@NotNull(message = "Patient id can't be empty") int id) {
        List<Diagnose> diagnoseList = null;

        if (validation.checkPatientId(id)) {
            diagnoseList = diagnoseRepository.getDiagnoseByPatientId(id).orElseThrow(
                    () -> new NoSuchUserException("No diagnose"));
        }

        return DiagnoseMapper.INSTANCE.toDto(diagnoseList);
    }
}
