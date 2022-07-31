package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.*;
import com.khmal.hospital.dao.repository.*;
import com.khmal.hospital.service.validator.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private HospitalStuffRepository hospitalStuffRepository;
    @Mock
    private StuffRoleRepository stuffRoleRepository;
    @Mock
    private Validation validation;
    @Mock
    private HospitalStuff hospitalStuff;


    @BeforeEach
    public void setUp() {
        Mockito.when(stuffRoleRepository.getStuffRoleById(Mockito.anyInt()))
                .thenReturn(Optional.of(new StuffRole("ROLE_PATIENT")));

        HospitalStuff doctor = new HospitalStuff(1,"john", "schwarc", "js",
                "surgeon", new StuffRole("ROLE_DOCTOR"), new ArrayList<Patient>());
        Mockito.when(hospitalStuffRepository.getHospitalStuffById(Mockito.anyInt())).thenReturn(Optional.of(doctor));

        Patient patient = new Patient("Jan", "Grabowski", "jb",
                LocalDate.now(),
                new StuffRole("ROLE_PATIENT"));
        Mockito.when(patientRepository.getPatientById(Mockito.anyInt())).thenReturn(Optional.of(patient));

        Mockito.when(validation.checkHospitalStuffId(Mockito.anyInt())).thenReturn(true);
        Mockito.when(validation.checkPatientId(Mockito.anyInt())).thenReturn(true);
        Mockito.when(validation.checkStuffRoleInDataBase(Mockito.anyInt())).thenReturn(true);
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
        assertEquals(expectedStuffRoleName, patient.getStuffRole().getRoleName());
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

        Mockito.when(stuffRoleRepository.getStuffRoleById(3)).thenReturn(Optional.of(new StuffRole("doctor")));

        registrationService.addNewEmployee("serg", "khm", "sh",
                "surgeon", 3);

        ArgumentCaptor<HospitalStuff> hospitalStuffDtoArgumentCaptor = ArgumentCaptor.forClass(HospitalStuff.class);
        Mockito.verify(hospitalStuffRepository).save(hospitalStuffDtoArgumentCaptor.capture());
        HospitalStuff hospitalStuff = hospitalStuffDtoArgumentCaptor.getValue();

        assertEquals(expectedDoctorSpecialization, hospitalStuff.getDoctorSpecialization());
    }

    @Test
    void appointDoctorToPatientPositiveCase(){

        Mockito.when(hospitalStuff.getPatientsList()).thenReturn(new ArrayList<Patient>());
        registrationService.appointDoctorToPatient(1,1);

        ArgumentCaptor<HospitalStuff> hospitalStuffArgumentCaptor = ArgumentCaptor.forClass(HospitalStuff.class);
        Mockito.verify(hospitalStuffRepository).save(hospitalStuffArgumentCaptor.capture());
        HospitalStuff hospitalStuff = hospitalStuffArgumentCaptor.getValue();

        assertEquals("Jan", hospitalStuff.getPatientsList().get(0).getFirstname());
    }
}