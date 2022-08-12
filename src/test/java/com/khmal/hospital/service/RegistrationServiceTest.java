package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.*;
import com.khmal.hospital.dao.repository.*;
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import com.khmal.hospital.service.validator.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private HospitalStaffRepository hospitalStaffRepository;
    @Mock
    private StaffRoleRepository staffRoleRepository;
    @Mock
    private Validation validation;

    @BeforeEach
    public void setUp() {
        Mockito.when(staffRoleRepository.getStuffRoleById(Mockito.anyInt()))
                .thenReturn(Optional.of(new StaffRole("ROLE_PATIENT")));

        HospitalStaff doctor = new HospitalStaff("john", "schwarc", "js",
                "surgeon", new StaffRole("ROLE_DOCTOR"));

        doctor.setPatientsList(new ArrayList<Patient>());

        Patient patient = new Patient("Jan", "Grabowski", "jb",
                LocalDate.now(), new StaffRole("ROLE_PATIENT"));

        Mockito.when(hospitalStaffRepository.getHospitalStuffById(Mockito.anyInt())).thenReturn(Optional.of(doctor));
        Mockito.when(patientRepository.getPatientById(Mockito.anyInt())).thenReturn(Optional.of(patient));

        Mockito.when(validation.checkHospitalStuffId(Mockito.anyInt())).thenReturn(true);
        Mockito.when(validation.checkPatientId(Mockito.anyInt())).thenReturn(true);
        Mockito.when(validation.checkStuffRoleInDataBase(Mockito.anyInt())).thenReturn(true);
        Mockito.when(validation.checkDoubleAppoint(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
    }

    @Test
    void addNewPatientPositiveCase() {
        String expectedUserName = "sh";
        String expectedFirstname = "serg";
        String expectedStuffRoleName = "ROLE_PATIENT";
        String lastname = "khm";
        int stuffRoleId = 4;

        registrationService.addNewPatient(expectedFirstname, lastname, expectedUserName,
                LocalDate.now(), stuffRoleId);

        ArgumentCaptor<Patient> patientArgumentCaptor = ArgumentCaptor.forClass(Patient.class);
        Mockito.verify(patientRepository).save(patientArgumentCaptor.capture());
        Patient patient = patientArgumentCaptor.getValue();

        assertEquals(expectedUserName, patient.getUsername());
        assertEquals(expectedFirstname, patient.getFirstname());
        assertEquals(expectedStuffRoleName, patient.getStaffRole().getRoleName());
    }

    @Test
    void addNewUserToSecurityTablePositiveCase() {
        String expectedUsername = "sh";
        String password = "{noop}12345";
        int expectedEnabled = 1;

        registrationService.addNewUserToSecurityTable(expectedUsername, password);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();

        assertEquals(expectedUsername, user.getUsername());
        assertEquals(expectedEnabled, user.getEnabled());
    }

    @Test
    void addNewUserRoleToSecurityTablePositiveCase() {
        String expectedStuffRole = "ROLE_PATIENT";
        String username = "sh";
        String password = "{noop}serg";
        int roleId = 4;

        Mockito.when(userRepository.getUserByUsername(username))
                .thenReturn(Optional.of(new User(username, password)));

        registrationService.addUserRoleToSecurityTable(username, roleId);

        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
        Mockito.verify(roleRepository).save(roleArgumentCaptor.capture());
        Role role = roleArgumentCaptor.getValue();

        assertEquals(expectedStuffRole, role.getRoleName());
}

    @Test
    void addNewEmployeePositiveCase() {
        String expectedDoctorSpecialization = "surgeon";

        Mockito.when(staffRoleRepository.getStuffRoleById(3)).thenReturn(Optional.of(new StaffRole("doctor")));

        registrationService.addNewEmployee("serg", "khm", "sh",
                "surgeon", 3);

        ArgumentCaptor<HospitalStaff> hospitalStuffDtoArgumentCaptor = ArgumentCaptor.forClass(HospitalStaff.class);
        Mockito.verify(hospitalStaffRepository).save(hospitalStuffDtoArgumentCaptor.capture());
        HospitalStaff hospitalStuff = hospitalStuffDtoArgumentCaptor.getValue();

        assertEquals(expectedDoctorSpecialization, hospitalStuff.getDoctorSpecialization());
    }

    @Test
    void appointDoctorToPatientPositiveCase(){
        registrationService.appointDoctorToPatient(1,1);

        ArgumentCaptor<HospitalStaff> hospitalStuffArgumentCaptor = ArgumentCaptor.forClass(HospitalStaff.class);
        Mockito.verify(hospitalStaffRepository).save(hospitalStuffArgumentCaptor.capture());
        HospitalStaff hospitalStuff = hospitalStuffArgumentCaptor.getValue();

        assertEquals("Jan", hospitalStuff.getPatientsList().get(0).getFirstname());
    }

    @Test
    void getAllPatientsPositiveCase(){
        List<Patient> patientList = new ArrayList<>();
        Patient firstPatient = new Patient();
        Patient secondPatient = new Patient();
        patientList.add(firstPatient);
        patientList.add(secondPatient);

        Mockito.when(patientRepository.findAll()).thenReturn(patientList);

        assertEquals(patientList.size(), registrationService.getAllPatients().size());
    }

    @Test
    void getAllPatientsNegativeCase(){
        Mockito.when(patientRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(IncorrectDateException.class, () -> registrationService.getAllPatients());
    }

    @Test
    void getAllDoctorsPositiveCase(){
        List<HospitalStaff> doctorList = new ArrayList<>();
        HospitalStaff firstDoctor = new HospitalStaff();
        doctorList.add(firstDoctor);

        Mockito.when(hospitalStaffRepository.getHospitalStuffByDoctorSpecializationIsNotNull())
                .thenReturn(Optional.of(doctorList));

        assertEquals(doctorList.size(), registrationService.getAllDoctors().size());
    }

    @Test
    void getAllDoctorsNegativeCase(){
        Mockito.when(hospitalStaffRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(NoSuchUserException.class, () -> registrationService.getAllDoctors());
    }
}