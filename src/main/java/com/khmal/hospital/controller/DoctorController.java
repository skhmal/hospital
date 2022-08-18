package com.khmal.hospital.controller;

import com.khmal.hospital.controller.exception.handling.IncorrectDataException;
import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.service.MedicalStaffService;
import com.khmal.hospital.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final MedicalStaffService medicalStaffService;
    private final SecurityService securityService;

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
    public String getDoctorAppointment(@ModelAttribute("appointments") AppointmentDto appointmentDto,
                                       @RequestParam("patientIdDoctorAppointment") int patientId,
                                       @RequestParam("appointmentTypeDoctor") String appointmentType,
                                       Principal principal) {

        int doctorId = securityService.getEmployeeId(principal.getName());

        medicalStaffService.createAppointment(patientId, doctorId, appointmentType,
                appointmentDto.getSummary(), appointmentDto.getDate());

        return "successful";
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

    public int getDoctorId(Principal principal) {
        return securityService.getEmployeeId(principal.getName());
    }

    @Transactional
    @RequestMapping(value = "/diagnose", method = RequestMethod.POST, params = "action=save")
    public String getDiagnose(@NotBlank(message = "Field summary is empty") @RequestParam("diagnoseSummary") String summary,
                              int patientId,
                              Principal principal) {

        int doctorId = getDoctorId(principal);

        if (patientId != 0) {

            medicalStaffService.createDiagnose(patientId, doctorId, summary);

        }else {
            throw new IncorrectDataException("Patient id can't be empty or 0");
        }
        return "successful";
    }
}
