package com.khmal.hospital.controller;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.service.MedicalStaffService;
import com.khmal.hospital.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final MedicalStaffService medicalStaffService;
    private final SecurityService securityService;
    private static final String SUCCESSFUL = "redirect:/successful";

    public DoctorController(MedicalStaffService medicalStaffService, SecurityService securityService) {
        this.medicalStaffService = medicalStaffService;
        this.securityService = securityService;
    }

    @GetMapping("/appointment")
    public String getDoctorAppointment(Model model, Principal principal) {
        int doctorId = securityService.getEmployeeId(principal.getName());
        List<PatientDto> patientDtoList = medicalStaffService.getDoctorPatients(doctorId);

        Appointment.DoctorAppointment[] doctorAppointments = Appointment.DoctorAppointment.values();

        model.addAttribute("appointmentType", doctorAppointments);
        model.addAttribute("appointments", new AppointmentDto());
        model.addAttribute("patients", patientDtoList);

        return "addAppointmentDoctor";
    }

    @Transactional
    @RequestMapping(value = "/appointment", method = RequestMethod.POST, params = "action=save")
    public String getDoctorAppointment(AppointmentDto appointmentDto,
                                       @RequestParam("patientIdDoctorAppointment") Integer patientId,
                                       @RequestParam("appointmentTypeDoctor") String appointmentType,
                                       Principal principal) {

        int doctorId = securityService.getEmployeeId(principal.getName());

        medicalStaffService.createAppointment(patientId, doctorId, appointmentType,
                appointmentDto.getSummary(), appointmentDto.getDate());

        return SUCCESSFUL;
    }

    @GetMapping("/diagnose")
    public String getDiagnose(Model model, Principal principal) {

        int doctorId = getDoctorId(principal);

        List<PatientDto> patientDtoList = medicalStaffService.getDoctorPatients(doctorId);

        Appointment.DoctorAppointment[] doctorAppointments = Appointment.DoctorAppointment.values();

        model.addAttribute("appointmentType", doctorAppointments);
        model.addAttribute("appointments", new AppointmentDto());
        model.addAttribute("patients", patientDtoList);
        model.addAttribute("doctorId", doctorId);

        return "createDiagnose";
    }

    @RequestMapping(value = "/diagnose", method = RequestMethod.POST, params = "action=save")
    public String getDiagnose(@RequestParam("diagnoseSummary") String summary,
                              @RequestParam("patientIdDoctorDiagnose") Integer patientId,
                              Principal principal) {

        Integer doctorId = getDoctorId(principal);

        medicalStaffService.createDiagnose(patientId, doctorId, summary);

        return SUCCESSFUL;
    }

    public int getDoctorId(Principal principal) {
        return securityService.getEmployeeId(principal.getName());
    }
}
