package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.HospitalStuff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.entity.StuffRole;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.HospitalStuffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedicalStuffServiceTest {

    @InjectMocks
    private MedicalStuffService appointmentService;

    @Mock
    private HospitalStuffRepository hospitalStuffRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Test
    void createAppointmentPositiveCase() {
        Mockito.when(hospitalStuffRepository.getHospitalStuffById(1)).thenReturn(
                Optional.of(new HospitalStuff("serg", "khm", "sh", "family doctor",
                        new StuffRole())));
        Mockito.when(patientRepository.getPatientById(1)).thenReturn(
                Optional.of(new Patient("thomas", "andersen", "neo", LocalDate.now(), false)));

        appointmentService.createAppointment(
                1,1,"medications", "ibuprom", LocalDateTime.now());

        ArgumentCaptor<Appointment> appointmentArgumentCaptor = ArgumentCaptor.forClass(Appointment.class);
        Mockito.verify(appointmentRepository).save(appointmentArgumentCaptor.capture());
        Appointment appointment = appointmentArgumentCaptor.getValue();

        assertEquals("ibuprom", appointment.getSummary());
        assertEquals("neo", appointment.getPatient().getUsername());
        assertEquals("khm", appointment.getHospitalStuff().getLastname());
    }
}