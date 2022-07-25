package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.HospitalStuff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.entity.Role;
import com.khmal.hospital.dao.entity.User;
import com.khmal.hospital.dao.repository.HospitalStuffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dao.repository.RoleRepository;
import com.khmal.hospital.dao.repository.UserRepository;
import com.khmal.hospital.dto.HospitalStuffDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.RoleDto;
import com.khmal.hospital.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

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

    @BeforeEach
    void setUp() {
//        roleRepository = Mockito.mock(RoleRepository.class);
//        patientRepository = Mockito.mock(PatientRepository.class);
//        userRepository = Mockito.mock(UserRepository.class);
//        hospitalStuffRepository = Mockito.mock(HospitalStuffRepository.class);
//
//        registrationService =
//                new RegistrationService(roleRepository, patientRepository, userRepository, hospitalStuffRepository);
    }


    @Test
    void addNewPatientPositiveCase() {
        PatientDto patientDto = new PatientDto("serg", "khm", "sh", LocalDate.now(), false);
        registrationService.addNewPatient(patientDto);

        ArgumentCaptor<Patient> patientArgumentCaptor = ArgumentCaptor.forClass(Patient.class);
        Mockito.verify(patientRepository).save(patientArgumentCaptor.capture());
        Patient patient = patientArgumentCaptor.getValue();

        assertEquals("sh", patient.getUsername());
    }

    @Test
    void addNewUserToSecurityTablePositiveCase() {
        UserDto userDto = new UserDto("sh", "{noop}12345", 1);
        registrationService.addNewUserToSecurityTable(userDto);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();

        assertEquals("sh", user.getUsername());
    }

    @Test
    void addNewUserRoleToSecurityTablePositiveCase() {
        RoleDto roleDto = new RoleDto("sh","ROLE_PATIENT");
        registrationService.addUserRoleToSecurityTable(roleDto);

        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
        Mockito.verify(roleRepository).save(roleArgumentCaptor.capture());
        Role role = roleArgumentCaptor.getValue();

        assertEquals("ROLE_PATIENT", role.getRoleName());
    }

    @Test
    void addNewEmployeePositiveCase(){
        HospitalStuffDto hospitalStuffDto = new HospitalStuffDto("serg", "khm", "sh", "surgeon", "ROLE_DOCTOR");
        registrationService.addNewEmployee(hospitalStuffDto);

        ArgumentCaptor<HospitalStuff> hospitalStuffDtoArgumentCaptor = ArgumentCaptor.forClass(HospitalStuff.class);
        Mockito.verify(hospitalStuffRepository).save(hospitalStuffDtoArgumentCaptor.capture());
        HospitalStuff hospitalStuff = hospitalStuffDtoArgumentCaptor.getValue();

        assertEquals("surgeon", hospitalStuff.getDoctorSpecialization());
    }
}