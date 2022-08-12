package com.khmal.hospital.service.validator;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.entity.StaffRole;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.HospitalStaffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dao.repository.StaffRoleRepository;
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    private HospitalStaffRepository hospitalStaffRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private StaffRoleRepository staffRoleRepository;


    @BeforeEach
    public void setUp(){
        HospitalStaff doctor = new HospitalStaff("john", "schwarc", "js",
                "surgeon", new StaffRole("ROLE_DOCTOR"));

        Patient patient = new Patient();
        patient.setId(7);

        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        doctor.setPatientsList(patientList);

        Mockito.when(hospitalStaffRepository.findHospitalStuffById(Mockito.anyInt()))
                .thenReturn(Optional.of(doctor));

        Mockito.when(patientRepository.findPatientById(Mockito.anyInt()))
                .thenReturn(Optional.of(new Patient()));
    }



    @Test
    void checkHospitalStuffIdPositiveCase() {

        Mockito.when(hospitalStaffRepository.findHospitalStuffById(Mockito.anyInt()))
                .thenReturn(Optional.of(new HospitalStaff()));

        assertTrue(validation.checkHospitalStuffId(1));
    }

    @Test
    void checkHospitalStuffIdNegativeCase() {
        Mockito.when(hospitalStaffRepository.findHospitalStuffById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class, () -> validation.checkHospitalStuffId(1));
    }


    @Test
    void checkPatientIdPositiveCase() {


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

        Mockito.when(appointmentRepository.findAppointmentByHospitalStaffId(Mockito.anyInt()))
                .thenReturn(Optional.of(new ArrayList<Appointment>()));

        assertTrue(validation.checkAppointmentDateForHospitalStuff(1, 1, appointmentTime));
    }

    @Test
    void checkAppointmentDateForHospitalStuffPositiveCaseWithoutAppointmentLists() {
        LocalDateTime appointmentTime = LocalDateTime.of(2022, 3, 1,
                13, 15);

        Mockito.when(appointmentRepository.findAppointmentByPatientId(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        Mockito.when(appointmentRepository.findAppointmentByHospitalStaffId(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        assertTrue(validation.checkAppointmentDateForHospitalStuff(1, 1, appointmentTime));
    }

    @Test
    void checkAppointmentDateForHospitalStaffNegativeCase() {
        LocalDateTime appointmentTime = LocalDateTime.of(2022, 3, 1,
                13, 15);
        Appointment appointment = new Appointment(appointmentTime, "consultation", "temperature check",
                new Patient(), new HospitalStaff());

        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);

        Mockito.when(appointmentRepository.findAppointmentByHospitalStaffId(Mockito.anyInt()))
                .thenReturn(Optional.of(appointmentList));
        Mockito.when(appointmentRepository.findAppointmentByPatientId(Mockito.anyInt()))
                .thenReturn(Optional.of(appointmentList));

        assertThrows(IncorrectDateException.class, () -> validation.checkAppointmentDateForHospitalStuff(
                1, 1, appointmentTime));
    }

    @Test
    void checkAppointmentDateForHospitalStaffNegativeCaseForPatient() {
        LocalDateTime appointmentTime = LocalDateTime.of(2022, 3, 1,
                13, 15);

        Appointment appointmentPatient = new Appointment(appointmentTime, "medications",
                "ibuprom", new Patient(),new HospitalStaff());

        List<Appointment> appointmentListPatient = new ArrayList<>();
        appointmentListPatient.add(appointmentPatient);

        List<Appointment> appointmentListMedic = new ArrayList<>();

        Mockito.when(appointmentRepository.findAppointmentByHospitalStaffId(Mockito.anyInt()))
                .thenReturn(Optional.of(appointmentListMedic));
        Mockito.when(appointmentRepository.findAppointmentByPatientId(Mockito.anyInt()))
                .thenReturn(Optional.of(appointmentListPatient));

        assertThrows(IncorrectDateException.class, () -> validation.checkAppointmentDateForHospitalStuff(
                1, 1, appointmentTime));
    }

    @Test
    void checkStuffRoleInDataBasePositiveCase() {
        Mockito.when(staffRoleRepository.getStuffRoleById(Mockito.anyInt())).thenReturn(Optional.of(new StaffRole()));

        assertTrue(validation.checkStuffRoleInDataBase(Mockito.anyInt()));
    }

    @Test
    void checkStuffRoleInDataBaseNegativeCase() {
        Mockito.when(staffRoleRepository.getStuffRoleById(Mockito.anyInt())).thenReturn(Optional.empty());

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

    @Test
    void checkAppointmentTypePositiveCase(){
        String appointmentType = "MEDICATIONS";

        assertTrue(validation.checkAppointmentType(appointmentType));
    }

    @Test
    void checkAppointmentTypeNegativeCase(){
        String appointmentType = "BIEDRONKA";

        assertThrows(IncorrectDateException.class, () -> validation.checkAppointmentType(appointmentType));
    }

    @Test
    void checkDoubleAppointPositiveCase(){
        assertTrue(validation.checkDoubleAppoint(Mockito.anyInt(),1));
    }

    @Test
    void checkDoubleAppointNegativeCase(){
        assertFalse(validation.checkDoubleAppoint(Mockito.anyInt(),7));
    }

    @Test
    void checkDoubleAppointNegativeCaseDoctorNotFound(){
        Mockito.when(hospitalStaffRepository.findHospitalStuffById(666)).thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class ,() ->validation.checkDoubleAppoint(666,Mockito.anyInt()));
    }
}