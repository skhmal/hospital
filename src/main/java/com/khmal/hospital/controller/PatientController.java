package com.khmal.hospital.controller;

import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.DiagnoseDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.service.PatientService;
import com.khmal.hospital.service.RegistrationService;
import com.khmal.hospital.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final SecurityService securityService;
    private final RegistrationService registrationService;
    private final PatientService patientService;

    public PatientController(SecurityService securityService, RegistrationService registrationService, PatientService patientService) {
        this.securityService = securityService;
        this.registrationService = registrationService;
        this.patientService = patientService;
    }

    @GetMapping("/appointments")
    public String getPatientAppointments(Model model, Principal principal){

        int patientId = securityService.getPatientId(principal);
        PatientDto patientDto = registrationService.getPatientById(patientId);
        List<AppointmentDto> appointmentDto = patientService.getPatientAppointments(patientId);
        List<DiagnoseDto> diagnoseDto = patientService.getPatientDiagnoses(patientId);

        model.addAttribute("isDischarge",patientDto.isDischarged());
        model.addAttribute("appointments", appointmentDto);
        model.addAttribute("diagnoses",diagnoseDto);

        return "patientView";
    }

    @GetMapping("/diagnoses")
    public String getPatientView(Model model, Principal principal){

        int patientId = securityService.getPatientId(principal);
        PatientDto patientDto = registrationService.getPatientById(patientId);
        List<AppointmentDto> appointmentDto = patientService.getPatientAppointments(patientId);
        List<DiagnoseDto> diagnoseDto = patientService.getPatientDiagnoses(patientId);

        model.addAttribute("isDischarge",patientDto.isDischarged());
        model.addAttribute("appointments", appointmentDto);
        model.addAttribute("diagnoses",diagnoseDto);

        return "patientView";
    }

}
