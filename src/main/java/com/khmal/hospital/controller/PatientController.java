package com.khmal.hospital.controller;

import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.DiagnoseDto;
import com.khmal.hospital.service.PatientService;
import com.khmal.hospital.service.RegistrationService;
import com.khmal.hospital.service.SecurityService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


    @GetMapping("/appointments/{pageNo}")
    public String findPaginatedPatientAppointments(@PathVariable(value = "pageNo") int pageNo,
                                                   @RequestParam("sortField") String sortField,
                                                   @RequestParam("sortDir") String sortDir,
                                                   Model model, Principal principal) {
        int pageSize = 5;

        int patientId = securityService.getPatientId(principal);

        Page<AppointmentDto> page = patientService.getAllPatientAppointmentsPaginated(pageNo, pageSize, sortField, sortDir, patientId);
        List<AppointmentDto> appointmentDtoList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("appointmentList", appointmentDtoList);
        return "patientAppointments";
    }

    @GetMapping("/diagnoses/{pageNo}")
    public String findPaginatedPatientDiagnoses(@PathVariable(value = "pageNo") int pageNo,
                                                @RequestParam("sortField") String sortField,
                                                @RequestParam("sortDir") String sortDir,
                                                Model model, Principal principal) {
        int pageSize = 5;

        int patientId = securityService.getPatientId(principal);

        Page<DiagnoseDto> page = patientService.getAllPatientDiagnosesPaginated(pageNo, pageSize, sortField,
                sortDir, patientId);
        List<DiagnoseDto> diagnoseDtoList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("diagnoseList", diagnoseDtoList);
        return "patientDiagnoses";
    }

    @GetMapping("/appointments")
    public String viewPatientAppointmentsPage(Model model, Principal principal) {
        return findPaginatedPatientAppointments(1, "date", "desc", model, principal);
    }

    @GetMapping("/diagnoses")
    public String viewPatientDiagnosesPage(Model model, Principal principal) {
        return findPaginatedPatientDiagnoses(1, "diagnoseDate", "desc", model, principal);
    }

}
