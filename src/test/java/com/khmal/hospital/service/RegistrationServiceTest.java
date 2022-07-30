package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.*;
import com.khmal.hospital.dao.repository.*;
import com.khmal.hospital.service.validator.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
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

    @BeforeEach
    public void setUp() {
        Mockito.when(stuffRoleRepository.getStuffRoleById(4))
                .thenReturn(Optional.of(new StuffRole("ROLE_PATIENT")));
        Mockito.when(validation.checkRoleInDataBase(Mockito.anyInt())).thenReturn(true);
    }

    @Test
    void addNewPatientPositiveCase() {
        String expectedUserName = "sh";
        String expectedFirstname = "serg";
        String expectedStuffRoleName = "ROLE_PATIENT";


        registrationService.addNewPatient("serg", "khm", "sh",
                LocalDate.now(), 4, false);

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
        int expectedEnabled = 1;

        registrationService.addNewUserToSecurityTable("sh", "{noop}12345", 1);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();

        assertEquals(expectedUsername, user.getUsername());
        assertEquals(expectedEnabled, user.getEnabled());
    }

    @Test
    void addNewUserRoleToSecurityTablePositiveCase() {
        String expectedStuffRole = "ROLE_PATIENT";

        Mockito.when(userRepository.getUserByUsername("sh")).thenReturn(Optional.of(new User("sh", 4)));

        registrationService.addUserRoleToSecurityTable("sh", 4);

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
}