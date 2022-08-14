package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.*;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.DiagnoseRepository;
import com.khmal.hospital.dao.repository.HospitalStaffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MedicalStaffServiceTest {

    @InjectMocks
    private MedicalStaffService medicalStaffService;

    @Mock
    private HospitalStaffRepository hospitalStaffRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private Validation validation;

    @Mock
    private DiagnoseRepository diagnoseRepository;

    private final String PATIENT_FIRSTNAME = "thomas";

    @BeforeEach
    public void setup(){

        Patient patient = new Patient(PATIENT_FIRSTNAME, "andersen", "neo", LocalDate.now(),
                new StaffRole("ROLE_PATIENT"));

        HospitalStaff doctor = new HospitalStaff("serg", "khm", "sh", "family doctor",
                new StaffRole("ROLE_DOCTOR"));

        List<Patient> patientList = new ArrayList<>();
        List<HospitalStaff> doctorList = new ArrayList<>();

        patientList.add(patient);
        patient.setDoctorsList(doctorList);

        doctorList.add(doctor);
        doctor.setPatientsList(patientList);

        Mockito.when(hospitalStaffRepository.getHospitalStuffById(Mockito.anyInt())).thenReturn(
                Optional.of(doctor));

        Mockito.when(patientRepository.getPatientById(Mockito.anyInt())).thenReturn(
                Optional.of(patient));

        Mockito.when(validation.checkHospitalStuffId(Mockito.anyInt())).thenReturn(true);
        Mockito.when(validation.checkPatientId(Mockito.anyInt())).thenReturn(true);
        Mockito.when(validation.checkHospitalStuffId(Mockito.anyInt())).thenReturn(true);
    }

    @Test
    void createAppointmentPositiveCase() {
        String expectedSummary = "ibuprom";
        String expectedUsername = "neo";
        String expectedLastname = "khm";

        Mockito.when(validation.checkAppointmentDateForHospitalStuff(Mockito.anyInt(), Mockito.anyInt(),
                Mockito.any(LocalDateTime.class))).thenReturn(true);

        medicalStaffService.createAppointment(
                1, 1, Mockito.anyString(), expectedSummary, LocalDateTime.now());

        ArgumentCaptor<Appointment> appointmentArgumentCaptor = ArgumentCaptor.forClass(Appointment.class);
        Mockito.verify(appointmentRepository).save(appointmentArgumentCaptor.capture());
        Appointment appointment = appointmentArgumentCaptor.getValue();

        assertEquals(expectedSummary, appointment.getSummary());
        assertEquals(expectedUsername, appointment.getPatient().getUsername());
        assertEquals(expectedLastname, appointment.getHospitalStaff().getLastname());
    }

    @Test
    void createDiagnosePositiveCase() {
        String expectedSummary = "appendicitis";
        String expectedSpecialization = "family doctor";

        medicalStaffService.createDiagnose(1, 1, expectedSummary);

        ArgumentCaptor<Diagnose> diagnoseArgumentCaptor = ArgumentCaptor.forClass(Diagnose.class);
        Mockito.verify(diagnoseRepository).save(diagnoseArgumentCaptor.capture());
        Diagnose diagnose = diagnoseArgumentCaptor.getValue();

        assertEquals(expectedSummary, diagnose.getSummary());
        assertEquals(PATIENT_FIRSTNAME, diagnose.getPatient().getFirstname());
        assertEquals(expectedSpecialization, diagnose.getHospitalStaff().getDoctorSpecialization());
    }

    @Test
    void getDoctorPatients(){
        String actualPatientName = medicalStaffService.getDoctorPatients(Mockito.anyInt()).get(0).getFirstname();

        assertEquals(PATIENT_FIRSTNAME, actualPatientName);
    }

    @Test
    void egtDoctorPatientsWithoutAnyPatient(){
        HospitalStaff doctor = new HospitalStaff();
        List<Patient> patientList = new ArrayList<>();
        doctor.setPatientsList(patientList);

        Mockito.when(hospitalStaffRepository.getHospitalStuffById(Mockito.anyInt())).thenReturn(Optional.of(doctor));

       assertThrows(IncorrectDateException.class, () -> medicalStaffService.getDoctorPatients(Mockito.anyInt()));
    }
}