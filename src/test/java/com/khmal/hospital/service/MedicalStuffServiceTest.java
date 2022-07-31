package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.*;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.DiagnoseRepository;
import com.khmal.hospital.dao.repository.HospitalStuffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.service.validator.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @BeforeEach
    public void setup(){
        Mockito.when(hospitalStuffRepository.getHospitalStuffById(1)).thenReturn(
                Optional.of(new HospitalStuff("serg", "khm", "sh", "family doctor",
                        new StuffRole("ROLE_DOCTOR"))));

        Mockito.when(patientRepository.getPatientById(1)).thenReturn(
                Optional.of(new Patient("thomas", "andersen",
                        "neo", LocalDate.of(1990, 3, 1),
                        new StuffRole("ROLE_PATIENT"))));
    }

    private void validationEmployeeAndPatientById() {
        Mockito.when(validation.checkPatientId(Mockito.anyInt())).thenReturn(true);
        Mockito.when(validation.checkHospitalStuffId(Mockito.anyInt())).thenReturn(true);
    }

    @Test
    void createAppointmentPositiveCase() {
        String expectedSummary = "ibuprom";
        String expectedUsername = "neo";
        String expectedLastname = "khm";

        validationEmployeeAndPatientById();
        Mockito.when(validation.checkAppointmentDateForHospitalStuff(Mockito.anyInt(), Mockito.anyInt(),
                Mockito.any(LocalDateTime.class))).thenReturn(true);

        medicalStuffService.createAppointment(
                1, 1, "medications", "ibuprom", LocalDateTime.now());

        ArgumentCaptor<Appointment> appointmentArgumentCaptor = ArgumentCaptor.forClass(Appointment.class);
        Mockito.verify(appointmentRepository).save(appointmentArgumentCaptor.capture());
        Appointment appointment = appointmentArgumentCaptor.getValue();

        assertEquals(expectedSummary, appointment.getSummary());
        assertEquals(expectedUsername, appointment.getPatient().getUsername());
        assertEquals(expectedLastname, appointment.getHospitalStuff().getLastname());
    }

    @Test
    void createDiagnosePositiveCase() {
        String expectedSummary = "appendicitis";
        String expectedFirstName = "thomas";
        String expectedSpecialization = "family doctor";

        validationEmployeeAndPatientById();

        medicalStuffService.createDiagnose(1, 1, "appendicitis");

        ArgumentCaptor<Diagnose> diagnoseArgumentCaptor = ArgumentCaptor.forClass(Diagnose.class);
        Mockito.verify(diagnoseRepository).save(diagnoseArgumentCaptor.capture());
        Diagnose diagnose = diagnoseArgumentCaptor.getValue();

        assertEquals(expectedSummary, diagnose.getSummary());
        assertEquals(expectedFirstName, diagnose.getPatient().getFirstname());
        assertEquals(expectedSpecialization, diagnose.getHospitalStuff().getDoctorSpecialization());
    }
}