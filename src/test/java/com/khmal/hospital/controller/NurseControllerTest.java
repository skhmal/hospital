package com.khmal.hospital.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khmal.hospital.Helper;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.HospitalStaffDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class NurseControllerTest extends IntegrationTestBaseClass {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NurseController nurseController;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String FIRSTNAME = "Bob";
    private final String LASTNAME = "Dillinger";
    private final String USERNAME = "db";
    private final LocalDate BIRTHDAY = LocalDate.of(1903, 6, 22);
    private final int ROLE_ID = 4;
    private final String ROLE_NURSE = "NURSE";
    private final String ROLE_PATIENT = "PATIENT";

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



    @Test
    void getNurseAppointmentMethodPostPositiveCase() throws Exception {


        AppointmentDto dto = new AppointmentDto(1, LocalDateTime.of(2023, 3, 11, 13, 15),
                "MEDICATIONS", new PatientDto(),
                "BOLEN", new HospitalStaffDto());

        this.mockMvc
                .perform(
                        post("/nurse/appointment")
                                .with(csrf())
                                .with(SecurityMockMvcRequestPostProcessors.user("basia").password("basia").roles("NURSE"))
                                .param("patientIdNurseAppointment", String.valueOf(1))
                                .param("appointmentTypeNurse", "MEDICATIONS")
                                .flashAttr("appointments", dto)
                                .principal(() -> "basia")
                )
                .andExpect(redirectedUrl("/successful"));

        assertEquals("BOLEN", patientService.getAllPatientAppointmentsPaginated(1, 5, "appointmentType",
                "desc", 1).map(AppointmentDto::getSummary).get().findFirst().orElse(null));
    }
}