package com.khmal.hospital.service.validator;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.entity.StaffRole;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.HospitalStaffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dao.repository.StaffRoleRepository;
import com.khmal.hospital.service.exception_handling.IncorrectDataException;
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


    private final LocalDateTime APPOINTMENT_TIME = LocalDateTime.of(2022, 3, 1,
            13, 15);
    private final Appointment APPOINTMENT = new Appointment(APPOINTMENT_TIME, "consultation",
            "temperature check", new Patient(), new HospitalStaff());

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

        assertTrue(validation.checkHospitalStaffId(1));
    }

    @Test
    void checkHospitalStuffIdNegativeCase() {
        Mockito.when(hospitalStaffRepository.findHospitalStuffById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class, () -> validation.checkHospitalStaffId(1));
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


        Mockito.when(appointmentRepository.findAppointmentByPatientId(Mockito.anyInt()))
                .thenReturn(Optional.of(new ArrayList<Appointment>()));

        Mockito.when(appointmentRepository.findAppointmentByHospitalStaffId(Mockito.anyInt()))
                .thenReturn(Optional.of(new ArrayList<Appointment>()));

        assertTrue(validation.checkAppointmentDateForHospitalStaff(1, 1, APPOINTMENT_TIME));
    }

    @Test
    void checkAppointmentDateForHospitalStuffPositiveCaseWithoutAppointmentLists() {

        Mockito.when(appointmentRepository.findAppointmentByPatientId(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        Mockito.when(appointmentRepository.findAppointmentByHospitalStaffId(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        assertTrue(validation.checkAppointmentDateForHospitalStaff(1, 1, APPOINTMENT_TIME));
    }

    @Test
    void checkAppointmentDateForHospitalStaffNegativeCase() {
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(APPOINTMENT);

        Mockito.when(appointmentRepository.findAppointmentByHospitalStaffId(Mockito.anyInt()))
                .thenReturn(Optional.of(appointmentList));
        Mockito.when(appointmentRepository.findAppointmentByPatientId(Mockito.anyInt()))
                .thenReturn(Optional.of(appointmentList));

        assertThrows(IncorrectDataException.class, () -> validation.checkAppointmentDateForHospitalStaff(
                1, 1, APPOINTMENT_TIME));
    }

    @Test
    void checkAppointmentDateForHospitalStaffNegativeCaseForPatient() {
        List<Appointment> appointmentListPatient = new ArrayList<>();
        appointmentListPatient.add(APPOINTMENT);

        List<Appointment> appointmentListMedic = new ArrayList<>();

        Mockito.when(appointmentRepository.findAppointmentByHospitalStaffId(Mockito.anyInt()))
                .thenReturn(Optional.of(appointmentListMedic));
        Mockito.when(appointmentRepository.findAppointmentByPatientId(Mockito.anyInt()))
                .thenReturn(Optional.of(appointmentListPatient));

        assertThrows(IncorrectDataException.class, () -> validation.checkAppointmentDateForHospitalStaff(
                1, 1, APPOINTMENT_TIME));
    }

    @Test
    void checkStuffRoleInDataBasePositiveCase() {
        Mockito.when(staffRoleRepository.getStuffRoleById(Mockito.anyInt())).thenReturn(Optional.of(new StaffRole()));

        assertTrue(validation.checkStaffRoleInDataBase(Mockito.anyInt()));
    }

    @Test
    void checkStuffRoleInDataBaseNegativeCase() {
        Mockito.when(staffRoleRepository.getStuffRoleById(Mockito.anyInt())).thenReturn(Optional.empty());

        assertThrows(IncorrectDataException.class,
                () -> validation.checkStaffRoleInDataBase(1));
    }

    @Test
    void checkDoctorSpecializationPositiveCase() {
        String doctorSpecializationName = "SURGEON";

        assertTrue(validation.checkDoctorSpecialization(doctorSpecializationName));
    }

    @Test
    void checkDoctorSpecializationNegativeCase() {
        String badDoctorSpecializationName = "HOME";

        assertThrows(IncorrectDataException.class,
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

        assertThrows(IncorrectDataException.class, () -> validation.checkAppointmentType(appointmentType));
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

        assertThrows(NoSuchUserException.class ,() ->validation.checkDoubleAppoint(666,1));
    }
}