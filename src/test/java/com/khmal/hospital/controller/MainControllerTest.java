package com.khmal.hospital.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String USERNAME = "Bobby";
    private final String ROLE_PATIENT = "PATIENT";
    private final String ROLE_DOCTOR = "ROLE_DOCTOR";
    private final String ROLE_ADMINISTARTOR = "ADMINISTRATOR";


    @Test
    void getIndexPage() throws Exception {
        this.mockMvc
                .perform(
                        get("/")
                                .with(SecurityMockMvcRequestPostProcessors.user(USERNAME).roles(ROLE_PATIENT)
                                ))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getMain() throws Exception {
        this.mockMvc
                .   perform(
                        get("/main")
                                .with(SecurityMockMvcRequestPostProcessors.user(USERNAME).roles(ROLE_ADMINISTARTOR)
                                ))
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }

    @Test
    void getSuccessful() throws Exception {
        this.mockMvc
                .   perform(
                        get("/successful")
                                .with(SecurityMockMvcRequestPostProcessors.user(USERNAME).roles(ROLE_ADMINISTARTOR)
                                ))
                .andExpect(status().isOk())
                .andExpect(view().name("successful"));
    }

    @Test
    void getLog() throws Exception {
        this.mockMvc
                .   perform(
                        get("/login")
                                .with(SecurityMockMvcRequestPostProcessors.user(USERNAME).roles(ROLE_ADMINISTARTOR)
                                ))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void getLogout() throws Exception {
        this.mockMvc
                .   perform(
                        get("/logout")
                                .with(SecurityMockMvcRequestPostProcessors.user(USERNAME).roles(ROLE_ADMINISTARTOR)
                                ))
                .andExpect(status().is3xxRedirection());
    }
}