package com.khmal.hospital.dao.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HospitalStaffTest {

    private final String FIRSTNAME = "Pablo";
    private final String LASTNAME = "Escobar";
    private final String USERNAME = "pe";
    private final String SPECIALIZATION = "NARCOS";
    private final int PATIENT_COUNT = 10;

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
        patient.setUsername("OLEG");

        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);

        hospitalStaff.setPatientsList(patientList);

        hospitalStaff.setPatientCount(10);
    }

    @Test
    void getId() {
        int id = 333;
        hospitalStaff.setId(333);

        assertEquals(id, hospitalStaff.getId());

    }

    @Test
    void getFirstname() {
        assertEquals(FIRSTNAME,hospitalStaff.getFirstname());
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
        assertEquals("OLEG", hospitalStaff.getPatientsList().get(0).getUsername());
    }

    @Test
    void getPatientCount() {
        assertEquals(PATIENT_COUNT, hospitalStaff.getPatientCount());
    }

    @Test
    void setId() {
        int id = 777;

        hospitalStaff.setId(777);

        assertEquals(id, hospitalStaff.getId());
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
        int patientCount = 2;

        List<Patient> patientList = new ArrayList<>();
        patientList.add(new Patient());
        patientList.add(new Patient());

        hospitalStaff.setPatientsList(patientList);

        assertEquals(patientCount, hospitalStaff.getPatientsList().size());

    }

    @Test
    void setPatientCount() {
        int count = 123;

        hospitalStaff.setPatientCount(count);

        assertEquals(count, hospitalStaff.getPatientCount());
    }
}