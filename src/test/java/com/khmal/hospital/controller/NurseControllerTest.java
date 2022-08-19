package com.khmal.hospital.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.entity.StaffRole;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dao.repository.UserRepository;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.HospitalStaffDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.mapper.HospitalStuffMapper;
import com.khmal.hospital.mapper.PatientMapper;
import com.khmal.hospital.mapper.StaffRoleMapper;
import com.khmal.hospital.service.MedicalStaffService;
import com.khmal.hospital.service.RegistrationService;
import com.khmal.hospital.service.SecurityService;
import com.khmal.hospital.service.validator.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NurseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NurseController nurseController;

    @InjectMocks
    private MedicalStaffService medicalStaffService;

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private Validation validation;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private SecurityService securityService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String FIRSTNAME = "Bob";
    private final String LASTNAME = "Dillinger";
    private final String USERNAME = "db";
    private final LocalDate BIRTHDAY = LocalDate.of(1903, 6, 22);
    private final int ROLE_ID = 4;
    private final String ROLE_NURSE = "NURSE";
    private final String ROLE_PATIENT = "PATIENT";

    StaffRole staffRoleNurse = new StaffRole("ROLE_NURSE");

    StaffRole staffRolePatient = new StaffRole("ROLE_PATIENT");

    List<PatientDto> patientDtoList = new ArrayList<>();
    PatientDto patientDto = new PatientDto(FIRSTNAME, LASTNAME, USERNAME, BIRTHDAY, false, ROLE_ID);

    List<Patient> patientList = new ArrayList<>();
    Patient patient = new Patient(FIRSTNAME, LASTNAME, USERNAME, BIRTHDAY, staffRolePatient);
    HospitalStaff nurse = new HospitalStaff(FIRSTNAME, LASTNAME, USERNAME, null, staffRoleNurse);

    @BeforeEach
    void setUp() {

        staffRolePatient.setId(4);
        staffRoleNurse.setId(2);
        staffRoleNurse.setRoleName("ROLE_NURSE");
        nurse.setStaffRole(staffRoleNurse);

        List<PatientDto> patientDtoList = new ArrayList<>();
        PatientDto patientDto = new PatientDto(FIRSTNAME, LASTNAME, USERNAME, BIRTHDAY, false, ROLE_ID);
        patientDtoList.add(patientDto);

        List<Patient> patientList = new ArrayList<>();
        Patient patient = new Patient(FIRSTNAME, LASTNAME, USERNAME, BIRTHDAY, staffRolePatient);
        patientList.add(patient);

        Mockito.when(patientRepository.findAll()).thenReturn(patientList);

        Mockito.when(registrationService.getAllPatients()).thenReturn(patientDtoList);

    }

    public String toJson(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    @Test
    void smokeTest() {
        assertNotNull(nurseController);
    }

    @Test
    void getNurseAppointmentMethodGetPositiveCase() throws Exception {

        this.mockMvc
                .perform(
                        get("/nurse/appointment")
                                .with(SecurityMockMvcRequestPostProcessors.user(USERNAME).roles(ROLE_NURSE)
                                ))
                .andExpect(status().isOk())
                .andExpect(view().name("createAppointmentNurse"))
                .andExpect(model().attributeExists("appointmentType"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attributeExists("appointments"));
    }

    @Test
    void getNurseAppointmentMethodGetNegativeCaseShouldBeStatus403() throws Exception {
        this.mockMvc
                .perform(
                        get("/nurse/appointment")
                                .with(SecurityMockMvcRequestPostProcessors.user(USERNAME).roles(ROLE_PATIENT)

                                ))
                .andExpect(status().isForbidden());
    }

    class PrincipalImpl implements Principal {

        @Override
        public String getName() {

            return USERNAME;
        }

    }

    @Test
    @Disabled
    @WithMockUser(username = "db", roles = {"NURSE"})
    void getNurseAppointmentMethodPostPositiveCase() throws Exception {
        PatientDto patientDto1 = new PatientDto(FIRSTNAME, LASTNAME, USERNAME, LocalDate.of(1990,2,1)
                ,false, 4);
        patientDto1.setId(1);
Principal principal = new PrincipalImpl();

        HospitalStaffDto hospitalStaffDto = HospitalStuffMapper.INSTANCE.toDto(nurse);
        hospitalStaffDto.setId(1);
        hospitalStaffDto.setStuffRole(StaffRoleMapper.INSTANCE.toDto(staffRoleNurse));
        hospitalStaffDto.setStuffRoleName("ROLE_NURSE");

        AppointmentDto appointmentDto = new AppointmentDto(Mockito.anyInt(), LocalDateTime.now(), "MEDICATIONS", patientDto1,"SUMMARY"
                , hospitalStaffDto
        );

        Mockito.when(medicalStaffService.createAppointment(1, 1, "MEDICATIONS",
                "SUMMARY", LocalDateTime.now())).thenReturn(appointmentDto);

        Mockito.when(securityService.getEmployeeId(Mockito.any())).thenReturn(1);

        this.mockMvc
                .perform(
                        post("/nurse/appointment")
                                .with(csrf())
                                .with(SecurityMockMvcRequestPostProcessors.user(USERNAME).roles(ROLE_NURSE))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content( toJson(appointmentDto))
                                .param("patientIdNurseAppointment",toJson(1))
                                .param("appointmentTypeNurse", toJson("MEDICATIONS"))
                                .principal(principal)
                )
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/success"));
    }


    @Test
    void testGetNurseAppointment() {
    }
}