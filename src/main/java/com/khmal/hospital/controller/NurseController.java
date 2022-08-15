
package com.khmal.hospital.controller;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.service.MedicalStaffService;
import com.khmal.hospital.service.RegistrationService;
import com.khmal.hospital.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/nurse")
public class NurseController {

    private final SecurityService securityService;
    private final MedicalStaffService medicalStaffService;
    private final RegistrationService registrationService;

    public NurseController(SecurityService securityService, MedicalStaffService medicalStaffService, RegistrationService registrationService) {
        this.securityService = securityService;
        this.medicalStaffService = medicalStaffService;
        this.registrationService = registrationService;
    }

    @GetMapping("/appointment")
    public String getNurseAppointment(Model model) {

        List<PatientDto> patientDtoList = registrationService.getAllPatients();
        Appointment.NurseAppointment[] nurseAppointments = Appointment.NurseAppointment.values();

        model.addAttribute("appointmentType", nurseAppointments);
        model.addAttribute("appointments", new AppointmentDto());
        model.addAttribute("patients", patientDtoList);

        return "createAppointmentNurse";
    }

    @RequestMapping(value = "/appointment", method = RequestMethod.POST, params = "action=save")
    public String getNurseAppointment(@ModelAttribute("appointments") AppointmentDto appointmentDto,
                                      @RequestParam("patientIdNurseAppointment") int patientId,
                                      @RequestParam("appointmentTypeNurse") String appointmentType,
                                      Principal principal) {

        int nurseId = securityService.getEmployeeId(principal.getName());

        medicalStaffService.createAppointment(patientId, nurseId, appointmentType,
                appointmentDto.getSummary(), appointmentDto.getDate());

        return "redirect:/successful";
    }
}
