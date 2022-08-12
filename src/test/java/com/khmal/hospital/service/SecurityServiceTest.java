package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.repository.HospitalStaffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SecurityServiceTest {

    @InjectMocks
    private SecurityService securityService;

    @Mock
    private HospitalStaffRepository hospitalStaffRepository;

    @Mock
    private PatientRepository patientRepository;

    private final int USER_ID = 3;
    private final String USERNAME = "kubus";
    private final String INCORRECT_USERNAME = "BEAVIS";

    @BeforeEach
    public void setUp(){
        HospitalStaff hospitalStaff = new HospitalStaff();
        hospitalStaff.setUsername(USERNAME);
        hospitalStaff.setId(USER_ID);

        Mockito.when(hospitalStaffRepository.findHospitalStuffByUsername(USERNAME))
                .thenReturn(Optional.of(hospitalStaff));

        Patient patient =new Patient();
        patient.setId(USER_ID);
        patient.setUsername(USERNAME);

        Mockito.when(patientRepository.findPatientByUsername(USERNAME)).thenReturn(Optional.of(patient));

    }

    @Test
    void getEmployeeIdPositiveCase() {

        assertEquals(USER_ID, securityService.getEmployeeId(USERNAME));
    }

    @Test
    void getEmployeeIdNegativeCase() {

        assertThrows(NoSuchUserException.class, () -> securityService.getEmployeeId(INCORRECT_USERNAME));
    }

    @Test
    void getPatientIdPositiveCase() {
        assertEquals(USER_ID, securityService.getPatientId(USERNAME));
    }

    @Test
    void getPatientIdNegativeCase(){
        assertThrows(NoSuchUserException.class, () -> securityService.getPatientId(INCORRECT_USERNAME));
    }
}