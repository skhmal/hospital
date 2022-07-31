package com.khmal.hospital.service.validator;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.HospitalStuff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.entity.StuffRole;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.HospitalStuffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dao.repository.StuffRoleRepository;
import com.khmal.hospital.service.RegistrationService;
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidationTest {

    @InjectMocks
    private Validation validation;
    @Mock
    private HospitalStuffRepository hospitalStuffRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private StuffRoleRepository stuffRoleRepository;

    @Test
    void checkHospitalStuffIdPositiveCase() {
        Mockito.when(hospitalStuffRepository.findHospitalStuffById(Mockito.anyInt()))
                .thenReturn(Optional.of(new HospitalStuff()));

        assertTrue(validation.checkHospitalStuffId(1));
    }

    @Test
    void checkHospitalStuffIdNegativeCase() {
        Mockito.when(hospitalStuffRepository.findHospitalStuffById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class, () -> validation.checkHospitalStuffId(1));
    }


    @Test
    void checkPatientIdPositiveCase() {
        Mockito.when(patientRepository.findPatientById(Mockito.anyInt()))
                .thenReturn(Optional.of(new Patient()));

        assertTrue(validation.checkPatientId(1));
    }

    @Test
    void checkPatientIdNegativeCase() {
        Mockito.when(patientRepository.findPatientById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class, () -> validation.checkPatientId(1));
    }

    @Test
    void checkAppointmentDateForHospitalStuffPositiveCase() {
        LocalDateTime appointmentTime = LocalDateTime.of(2022, 3, 1,
                13, 15);

        Mockito.when(appointmentRepository.findAppointmentByPatientId(Mockito.anyInt()))
                .thenReturn(Optional.of(new ArrayList<Appointment>()));

        Mockito.when(appointmentRepository.findAppointmentByHospitalStuffId(Mockito.anyInt()))
                .thenReturn(Optional.of(new ArrayList<Appointment>()));

        assertTrue(validation.checkAppointmentDateForHospitalStuff(1, 1, appointmentTime));
    }

    @Test
    void checkAppointmentDateForHospitalStuffPositiveCaseWithoutAppointmentLists() {
        LocalDateTime appointmentTime = LocalDateTime.of(2022, 3, 1,
                13, 15);

        Mockito.when(appointmentRepository.findAppointmentByPatientId(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        Mockito.when(appointmentRepository.findAppointmentByHospitalStuffId(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        assertTrue(validation.checkAppointmentDateForHospitalStuff(1, 1, appointmentTime));
    }

    @Test
    void checkAppointmentDateForHospitalStuffNegativeCase() {
        LocalDateTime appointmentTime = LocalDateTime.of(2022, 3, 1,
                13, 15);
        Appointment appointment = new Appointment(appointmentTime, "consultation", "temperature check",
                new Patient(), new HospitalStuff());
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);

        Mockito.when(appointmentRepository.findAppointmentByHospitalStuffId(Mockito.anyInt()))
                .thenReturn(Optional.of(appointmentList));
        Mockito.when(appointmentRepository.findAppointmentByPatientId(Mockito.anyInt()))
                .thenReturn(Optional.of(appointmentList));

        assertThrows(IncorrectDateException.class, () -> validation.checkAppointmentDateForHospitalStuff(
                1, 1, appointmentTime));
    }

    @Test
    void checkStuffRoleInDataBasePositiveCase() {
        Mockito.when(stuffRoleRepository.getStuffRoleById(Mockito.anyInt())).thenReturn(Optional.of(new StuffRole()));

        assertTrue(validation.checkStuffRoleInDataBase(Mockito.anyInt()));
    }

    @Test
    void checkStuffRoleInDataBaseNegativeCase() {
        Mockito.when(stuffRoleRepository.getStuffRoleById(Mockito.anyInt())).thenReturn(Optional.empty());

        assertThrows(IncorrectDateException.class,
                () -> validation.checkStuffRoleInDataBase(Mockito.anyInt()));
    }

    @Test
    void checkDoctorSpecializationPositiveCase() {
        String doctorSpecializationName = "SURGEON";

        assertTrue(validation.checkDoctorSpecialization(doctorSpecializationName));
    }

    @Test
    void checkDoctorSpecializationNegativeCase() {
        String badDoctorSpecializationName = "HOME";

        assertThrows(IncorrectDateException.class,
                () -> validation.checkDoctorSpecialization(badDoctorSpecializationName));
    }
}