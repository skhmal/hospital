package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.Diagnose;
import com.khmal.hospital.dao.entity.HospitalStuff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.DiagnoseRepository;
import com.khmal.hospital.dao.repository.HospitalStuffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.DiagnoseDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.mapper.AppointmentMapper;
import com.khmal.hospital.mapper.DiagnoseMapper;
import com.khmal.hospital.mapper.PatientMapper;
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import com.khmal.hospital.service.validator.Validation;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
public class MedicalStaffService {
    private final PatientRepository patientRepository;
    private final HospitalStuffRepository hospitalStuffRepository;
    private final AppointmentRepository appointmentRepository;
    private final Validation validation;
    private final DiagnoseRepository diagnoseRepository;

    public MedicalStaffService(PatientRepository patientRepository, HospitalStuffRepository hospitalStuffRepository, AppointmentRepository appointmentRepository, Validation validation, DiagnoseRepository diagnoseRepository) {
        this.patientRepository = patientRepository;
        this.hospitalStuffRepository = hospitalStuffRepository;
        this.appointmentRepository = appointmentRepository;
        this.validation = validation;
        this.diagnoseRepository = diagnoseRepository;
    }

    public AppointmentDto createAppointment(int patientId,
                                            @NotNull(message = "Employee can't be empty") int hospitalStuffId,
                                            @NotBlank(message = "Appointment type can't be empty") String appointmentType,
                                            @NotBlank(message = "Summary can't be empty") String appointmentSummary,
                                            @NotNull(message = "Date can't be empty") LocalDateTime appointmentDate) {
        Appointment appointment = null;
        validation.checkAppointmentType(appointmentType);

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

    @Transactional
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

            Patient patient = patientRepository.getPatientById(patientId).get();

            if(patient.getDoctorsList().size() == 1){
                patient.setDischarged(true);
                patientRepository.save(patient);
            }

            HospitalStuff doctor = hospitalStuffRepository.getHospitalStuffById(doctorId).get();
            doctor.getPatientsList().remove(patient);
            doctor.setPatientCount(doctor.getPatientCount()-1);
        }
        return DiagnoseMapper.INSTANCE.toDto(diagnoseRepository.save(diagnose));
    }

    public List<DiagnoseDto> getPatientDiagnoses(@NotNull(message = "Patient id can't be empty") int id) {
        List<Diagnose> diagnoseList = null;

        if (validation.checkPatientId(id)) {
            diagnoseList = diagnoseRepository.getDiagnoseByPatientId(id).orElseThrow(
                    () -> new NoSuchUserException("No diagnose for patient"));
        }
        return DiagnoseMapper.INSTANCE.toDto(diagnoseList);
    }

    public List<PatientDto> getDoctorPatients(int doctorId){
       if  (hospitalStuffRepository.getHospitalStuffById(doctorId).isPresent()){
           HospitalStuff doctor = hospitalStuffRepository.getHospitalStuffById(doctorId).get();
           return PatientMapper.INSTANCE.toDto(doctor.getPatientsList());
       }else {
           throw new IncorrectDateException("Doctor doesn't have a patients for appointment");
       }
    }
}
