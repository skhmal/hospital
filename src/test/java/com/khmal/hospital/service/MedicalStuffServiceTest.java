package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.*;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.DiagnoseRepository;
import com.khmal.hospital.dao.repository.HospitalStuffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.service.validator.Validation;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MedicalStuffServiceTest {

    @InjectMocks
    private MedicalStuffService medicalStuffService;

    @Mock
    private HospitalStuffRepository hospitalStuffRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private Validation validation;

    @Mock
    private DiagnoseRepository diagnoseRepository;

    @Test
    void createAppointmentPositiveCase() {

        Mockito.when(hospitalStuffRepository.getHospitalStuffById(1)).thenReturn(
                Optional.of(new HospitalStuff("serg", "khm", "sh", "family doctor",
                        new StuffRole("ROLE_DOCTOR"))));

        Mockito.when(patientRepository.getPatientById(1)).thenReturn(
                Optional.of(new Patient("thomas", "andersen",
                        "neo", LocalDate.of(1990, 3, 1), false)));

        Mockito.when(validation.checkPatientId(1)).thenReturn(true);
        Mockito.when(validation.checkHospitalStuffId(1)).thenReturn(true);
        Mockito.when(validation.checkAppointmentDateForHospitalStuff(1, 1, LocalDateTime.of(
                LocalDate.of(2022, 12, 12), LocalTime.of(20, 11)
        ))).thenReturn(true);

        medicalStuffService.createAppointment(
                1, 1, "medications", "ibuprom", LocalDateTime.of(
                        LocalDate.of(2022, 12, 12), LocalTime.of(20, 11)));

        ArgumentCaptor<Appointment> appointmentArgumentCaptor = ArgumentCaptor.forClass(Appointment.class);
        Mockito.verify(appointmentRepository).save(appointmentArgumentCaptor.capture());
        Appointment appointment = appointmentArgumentCaptor.getValue();

        assertEquals("ibuprom", appointment.getSummary());
        assertEquals("neo", appointment.getPatient().getUsername());
        assertEquals("khm", appointment.getHospitalStuff().getLastname());
    }

    @Test
    void createDiagnosePositiveCase() {

        Mockito.when(hospitalStuffRepository.getHospitalStuffById(1)).thenReturn(
                Optional.of(new HospitalStuff("serg", "khm", "sh", "family doctor",
                        new StuffRole("ROLE_DOCTOR"))));

        Mockito.when(patientRepository.getPatientById(1)).thenReturn(
                Optional.of(new Patient("thomas", "andersen",
                        "neo", LocalDate.of(1990, 3, 1), false)));

        Mockito.when(validation.checkPatientId(1)).thenReturn(true);
        Mockito.when(validation.checkHospitalStuffId(1)).thenReturn(true);

        medicalStuffService.createDiagnose(1, 1, "appendicitis");

        ArgumentCaptor<Diagnose> diagnoseArgumentCaptor = ArgumentCaptor.forClass(Diagnose.class);
        Mockito.verify(diagnoseRepository).save(diagnoseArgumentCaptor.capture());
        Diagnose diagnose = diagnoseArgumentCaptor.getValue();

        assertEquals("appendicitis", diagnose.getSummary());
        assertEquals("thomas", diagnose.getPatient().getFirstname());
        assertEquals("family doctor", diagnose.getHospitalStuff().getDoctorSpecialization());
    }
}