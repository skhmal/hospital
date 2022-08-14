package com.khmal.hospital.dao.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HospitalStaffTest {

    private final int ID = 333;
    int PATIENT_COUNT = 2;
    private final String FIRSTNAME = "Pablo";
    private final String LASTNAME = "Escobar";
    private final String USERNAME = "pe";
    private final String SPECIALIZATION = "NARCOS";
    private final String TEST_STRING = "TEST";
    private final StaffRole STAFF_ROLE = new StaffRole("BOSS");

    private final HospitalStaff hospitalStaff = new HospitalStaff(
            FIRSTNAME,
            LASTNAME,
            USERNAME,
            SPECIALIZATION,
            STAFF_ROLE
    );

    @BeforeEach
    void setUp() {
        Patient patient = new Patient();
        patient.setUsername(USERNAME);

        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);

        hospitalStaff.setPatientsList(patientList);

        hospitalStaff.setPatientCount(PATIENT_COUNT);
    }

    @Test
    void getId() {
        hospitalStaff.setId(ID);

        assertEquals(ID, hospitalStaff.getId());

    }

    @Test
    void getFirstname() {
        assertEquals(FIRSTNAME, hospitalStaff.getFirstname());
    }

    @Test
    void getLastname() {
        assertEquals(LASTNAME, hospitalStaff.getLastname());
    }

    @Test
    void getUsername() {
        assertEquals(USERNAME, hospitalStaff.getUsername());
    }

    @Test
    void getDoctorSpecialization() {
        assertEquals(SPECIALIZATION, hospitalStaff.getDoctorSpecialization());
    }

    @Test
    void getStaffRole() {
        assertEquals(STAFF_ROLE, hospitalStaff.getStaffRole());
    }

    @Test
    void getPatientsList() {
        assertEquals(USERNAME, hospitalStaff.getPatientsList().get(0).getUsername());
    }

    @Test
    void getPatientCount() {
        assertEquals(PATIENT_COUNT, hospitalStaff.getPatientCount());
    }

    @Test
    void setId() {
        hospitalStaff.setId(ID);

        assertEquals(ID, hospitalStaff.getId());
    }

    @Test
    void setFirstname() {
        hospitalStaff.setFirstname(TEST_STRING);

        assertEquals(TEST_STRING, hospitalStaff.getFirstname());
    }

    @Test
    void setLastname() {
        hospitalStaff.setLastname(TEST_STRING);

        assertEquals(TEST_STRING, hospitalStaff.getLastname());
    }

    @Test
    void setUsername() {
        hospitalStaff.setUsername(TEST_STRING);

        assertEquals(TEST_STRING, hospitalStaff.getUsername());
    }

    @Test
    void setDoctorSpecialization() {
        hospitalStaff.setDoctorSpecialization(TEST_STRING);

        assertEquals(TEST_STRING, hospitalStaff.getDoctorSpecialization());
    }

    @Test
    void setStaffRole() {
        StaffRole staffRole = new StaffRole(TEST_STRING);
        hospitalStaff.setStaffRole(staffRole);

        assertEquals(TEST_STRING, hospitalStaff.getStaffRole().getRoleName());
    }

    @Test
    void setPatientsList() {
        List<Patient> patientList = new ArrayList<>();
        patientList.add(new Patient());
        patientList.add(new Patient());

        hospitalStaff.setPatientsList(patientList);

        assertEquals(PATIENT_COUNT, hospitalStaff.getPatientsList().size());
    }

    @Test
    void setPatientCount() {
        hospitalStaff.setPatientCount(PATIENT_COUNT);

        assertEquals(PATIENT_COUNT, hospitalStaff.getPatientCount());
    }
}