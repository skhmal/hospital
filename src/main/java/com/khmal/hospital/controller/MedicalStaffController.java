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

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/medic")
public class MedicalStaffController {

    private final MedicalStaffService medicalStaffService;
    private final SecurityService securityService;
    private final RegistrationService registrationService;

    public MedicalStaffController(MedicalStaffService medicalStaffService, SecurityService securityService, RegistrationService registrationService) {
        this.medicalStaffService = medicalStaffService;
        this.securityService = securityService;
        this.registrationService = registrationService;
    }


    @GetMapping("/appointment")
    public String getDoctorAppointment(Model model, Principal principal) {
        int medicId = getMedicId(principal);

        List<PatientDto> patientDtoDoctorList = medicalStaffService.getDoctorPatients(medicId);
        List<PatientDto> allPatientDtoList = registrationService.getAllPatients();
        System.out.println(allPatientDtoList);

        Appointment.DoctorAppointment[] doctorAppointments = Appointment.DoctorAppointment.values();
        Appointment.NurseAppointment[] nurseAppointments = Appointment.NurseAppointment.values();

        model.addAttribute("appointmentTypeDoctor", doctorAppointments);
        model.addAttribute("appointmentTypeNurse", nurseAppointments);
        model.addAttribute("appointments", new AppointmentDto());
        model.addAttribute("patients", patientDtoDoctorList);
        model.addAttribute("allPatients", allPatientDtoList);

        return "addAppointmentDoctor";
    }

    @Transactional
    @RequestMapping(value = "/appointment", method = RequestMethod.POST, params = "action=save")
    public String getDoctorAppointment(@ModelAttribute("appointments") AppointmentDto appointmentDto,
                                       @RequestParam("patientIdDoctorAppointment") int patientId,
                                       @RequestParam("appointmentTypeDoctor") String appointmentType,
                                       @RequestParam("appointmentTypeNurse") String appointmentTypeNurse,
                                       @RequestParam("patientIdNurseAppointment") int patientIdNurse,
                                       Principal principal) {

        int doctorId = securityService.getEmployeeId(principal);

        if ((patientId != 0) && (appointmentType != null)) {
            medicalStaffService.createAppointment(patientId, doctorId, appointmentType,
                    appointmentDto.getSummary(), appointmentDto.getDate());
        } else if ((patientIdNurse != 0) && (appointmentTypeNurse != null)) {
            medicalStaffService.createAppointment(patientIdNurse, doctorId, appointmentTypeNurse,
                    appointmentDto.getSummary(), appointmentDto.getDate());
        } else {
            return "error";
        }

        return "successful";
    }


    @GetMapping("/diagnose")
    public String getDiagnose(Model model, Principal principal) {
        int doctorId = getMedicId(principal);
        List<PatientDto> patientDtoList = medicalStaffService.getDoctorPatients(doctorId);
        Appointment.DoctorAppointment[] doctorAppointments = Appointment.DoctorAppointment.values();

        model.addAttribute("appointmentType", doctorAppointments);
        model.addAttribute("appointments", new AppointmentDto());
        model.addAttribute("patients", patientDtoList);
        model.addAttribute("doctorId", doctorId);

        return "createDiagnose";
    }

    public int getMedicId(Principal principal) {
        return securityService.getEmployeeId(principal);
    }

    @Transactional
    @RequestMapping(value = "/diagnose", method = RequestMethod.POST, params = "action=save")
    public String getDiagnose(@NotBlank(message = "Field summary is empty") @RequestParam("diagnoseSummary") String summary,
                              @NotNull(message = "Field patient is empty") @RequestParam("patientIdDoctorDiagnose") int patientId,
                              Principal principal) {

        int doctorId = getMedicId(principal);

        medicalStaffService.createDiagnose(patientId, doctorId, summary);

        return "successful";
    }
}
