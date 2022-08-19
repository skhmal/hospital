package com.khmal.hospital.controller;

import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.entity.StaffRole;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.HospitalStaffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.service.MedicalStaffService;
import com.khmal.hospital.service.SecurityService;
import com.khmal.hospital.service.validator.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorController doctorController;

    @Mock
    private MedicalStaffService medicalStaffService;

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private Validation validation;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private HospitalStaffRepository hospitalStaffRepository;

    @InjectMocks
    private SecurityService securityService;

    private final String USERNAME = "Bobby";
    private final String ROLE_PATIENT = "PATIENT";
    private final String ROLE_DOCTOR = "DOCTOR";
    private final String ROLE_ADMINISTARTOR = "ADMINISTRATOR";


    private final String FIRSTNAME = "Bob";
    private final String LASTNAME = "Dillinger";

    private final LocalDate BIRTHDAY = LocalDate.of(1903, 6, 22);
    private final int ROLE_ID = 4;
    private final String ROLE_NURSE = "NURSE";

    StaffRole staffRoleDoctor = new StaffRole("ROLE_DOCTOR");

    StaffRole staffRolePatient = new StaffRole("ROLE_PATIENT");
    List<PatientDto> patientDtoList = new ArrayList<>();

    List<Patient> patientList = new ArrayList<>();
    Patient patient = new Patient(FIRSTNAME, LASTNAME, USERNAME, BIRTHDAY, staffRolePatient);
    PatientDto patientDto = new PatientDto(FIRSTNAME, LASTNAME, USERNAME, BIRTHDAY, false, ROLE_ID);
    HospitalStaff doctor = new HospitalStaff(FIRSTNAME, LASTNAME, USERNAME, "SURGEON", staffRoleDoctor);

    @BeforeEach
    void setUp() {
        staffRoleDoctor.setId(3);

        patientDtoList.add(patientDto);

        patientList.add(patient);

        doctor.setPatientsList(patientList);
    }

    @Test
    void smokeTest() {
        assertNotNull(doctorController);
    }

    class PrincipalImpl implements Principal {

        @Override
        public String getName() {

            return USERNAME;
        }
    }
}