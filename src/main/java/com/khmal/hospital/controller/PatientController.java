package com.khmal.hospital.controller;

import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.DiagnoseDto;
import com.khmal.hospital.service.PatientService;
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
    private final PatientService patientService;
    private static final String DESC_SORT = "DESC";
    private static final String ASC_SORT = "ASC";
    private static final int PAGE_SIZE = 5;

    public PatientController(SecurityService securityService, PatientService patientService) {
        this.securityService = securityService;
        this.patientService = patientService;
    }


    @GetMapping("/appointments/{pageNo}")
    public String findPaginatedPatientAppointments(@PathVariable(value = "pageNo") int pageNo,
                                                   @RequestParam("sortField") String sortField,
                                                   @RequestParam("sortDir") String sortDir,
                                                   Model model, Principal principal) {

        int patientId = securityService.getPatientId(principal.getName());

        Page<AppointmentDto> page = patientService.getAllPatientAppointmentsPaginated(pageNo, PAGE_SIZE, sortField, sortDir, patientId);
        List<AppointmentDto> appointmentDtoList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? DESC_SORT : ASC_SORT);

        model.addAttribute("appointmentList", appointmentDtoList);
        return "patientAppointments";
    }

    @GetMapping("/diagnoses/{pageNo}")
    public String findPaginatedPatientDiagnoses(@PathVariable(value = "pageNo") int pageNo,
                                                @RequestParam("sortField") String sortField,
                                                @RequestParam("sortDir") String sortDir,
                                                Model model, Principal principal) {

        int patientId = securityService.getPatientId(principal.getName());

        Page<DiagnoseDto> page = patientService.getAllPatientDiagnosesPaginated(pageNo, PAGE_SIZE, sortField,
                sortDir, patientId);
        List<DiagnoseDto> diagnoseDtoList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? DESC_SORT : ASC_SORT);

        model.addAttribute("diagnoseList", diagnoseDtoList);
        return "patientDiagnoses";
    }

    @GetMapping("/appointments")
    public String viewPatientAppointmentsPage(Model model, Principal principal) {
        return findPaginatedPatientAppointments(1, "date", DESC_SORT, model, principal);
    }

    @GetMapping("/diagnoses")
    public String viewPatientDiagnosesPage(Model model, Principal principal) {
        return findPaginatedPatientDiagnoses(1, "diagnoseDate", DESC_SORT, model, principal);
    }
}
