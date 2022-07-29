package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.*;
import com.khmal.hospital.dao.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Test
    void addNewPatientPositiveCase() {
        registrationService.addNewPatient("serg", "khm", "sh", LocalDate.now(), false);

        ArgumentCaptor<Patient> patientArgumentCaptor = ArgumentCaptor.forClass(Patient.class);
        Mockito.verify(patientRepository).save(patientArgumentCaptor.capture());
        Patient patient = patientArgumentCaptor.getValue();

        assertEquals("sh", patient.getUsername());
    }

    @Test
    void addNewUserToSecurityTablePositiveCase() {
        registrationService.addNewUserToSecurityTable("sh", "{noop}12345", 1);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();

        assertEquals("sh", user.getUsername());
    }

    @Test
    void addNewUserRoleToSecurityTablePositiveCase() {

        Mockito.when(userRepository.getUserByUsername("sh")).thenReturn(Optional.of(new User("sh", 4)));
        Mockito.when(roleRepository.getRoleById(1)).thenReturn(
                Optional.of(new Role(
                        new User("sh", 4),
                        "ROLE_PATIENT")));
        registrationService.addUserRoleToSecurityTable("sh",1);

        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
        Mockito.verify(roleRepository).save(roleArgumentCaptor.capture());
        Role role = roleArgumentCaptor.getValue();

        assertEquals("ROLE_PATIENT", role.getRoleName());
    }

    @Test
    void addNewEmployeePositiveCase(){
        Mockito.when(stuffRoleRepository.getStuffRoleById(3)).thenReturn(Optional.of(new StuffRole("doctor")));

        registrationService.addNewEmployee("serg", "khm", "sh", "surgeon", 3);

        ArgumentCaptor<HospitalStuff> hospitalStuffDtoArgumentCaptor = ArgumentCaptor.forClass(HospitalStuff.class);
        Mockito.verify(hospitalStuffRepository).save(hospitalStuffDtoArgumentCaptor.capture());
        HospitalStuff hospitalStuff = hospitalStuffDtoArgumentCaptor.getValue();

        assertEquals("surgeon", hospitalStuff.getDoctorSpecialization());
    }
}