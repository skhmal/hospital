//package com.khmal.hospital.controller;
//
//import com.khmal.hospital.dao.entity.Appointment;
//import com.khmal.hospital.dao.entity.HospitalStuff;
//import com.khmal.hospital.dto.AppointmentDto;
//import com.khmal.hospital.dto.HospitalStuffDto;
//import com.khmal.hospital.dto.PatientDto;
//import com.khmal.hospital.dto.request.HospitalStuffDtoUserDtoRoleDto;
//import com.khmal.hospital.dto.request.PatientDtoUserDtoRoleDto;
//import com.khmal.hospital.service.MedicalStaffService;
//import com.khmal.hospital.service.RegistrationService;
//import com.khmal.hospital.service.SecurityService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.transaction.Transactional;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//import java.security.Principal;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/")
//public class WebController {
//
//    private final RegistrationService registrationService;
//    private final SecurityService securityService;
//    private final MedicalStaffService medicalStuffService;
//
//    public WebController(RegistrationService registrationService, SecurityService securityService, MedicalStaffService medicalStuffService) {
//        this.registrationService = registrationService;
//        this.securityService = securityService;
//        this.medicalStuffService = medicalStuffService;
//    }
//
//
//    @GetMapping("/administrator/patient")
//    public String createNewPatient(Model model) {
//        model.addAttribute("patient", new PatientDtoUserDtoRoleDto());
//        return "addPatient";
//    }
//
//    @Transactional
//    @RequestMapping(value = "/administrator/patient", method = RequestMethod.POST, params = "action=save")
//    public String createNewPatient(@ModelAttribute("patient") PatientDtoUserDtoRoleDto patientDtoUserDtoRoleDto) {
//        int patientRoleId = 4;
//
//        registrationService.addNewPatient(
//                patientDtoUserDtoRoleDto.getFirstName(),
//                patientDtoUserDtoRoleDto.getLastname(),
//                patientDtoUserDtoRoleDto.getUsername(),
//                patientDtoUserDtoRoleDto.getBirthday(),
//                patientRoleId
//        );
//
//        registrationService.addNewUserToSecurityTable(
//                patientDtoUserDtoRoleDto.getUsername(),
//                patientDtoUserDtoRoleDto.getPassword()
//        );
//
//        registrationService.addUserRoleToSecurityTable(
//                patientDtoUserDtoRoleDto.getUsername(),
//                patientRoleId
//        );
//        return "successful";
//    }
//
//    @GetMapping("/administrator/doctor")
//    public String createNewDoctor(Model model, Principal principal1) {
//        model.addAttribute("doctor", new HospitalStuffDtoUserDtoRoleDto());
//        model.addAttribute("specializations", HospitalStuff.DoctorSpecialization.values());
//        return "addDoctor";
//    }
//
//    @Transactional
//    @RequestMapping(value = "/administrator/doctor", method = RequestMethod.POST, params = "action=save")
//    public String createNewDoctor(@ModelAttribute("doctor") HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto,
//                                  @RequestParam(value = "doctorSpecialization") String doctorSpecialization) {
//        int doctorRoleId = 3;
//        registrationService.addNewEmployee(
//                hospitalStuffDtoUserDtoRoleDto.getFirstname(),
//                hospitalStuffDtoUserDtoRoleDto.getLastname(),
//                hospitalStuffDtoUserDtoRoleDto.getUsername(),
//                doctorSpecialization,
//                doctorRoleId
//        );
//
//        registrationService.addNewUserToSecurityTable(
//                hospitalStuffDtoUserDtoRoleDto.getUsername(),
//                hospitalStuffDtoUserDtoRoleDto.getPassword());
//
//        registrationService.addUserRoleToSecurityTable(
//                hospitalStuffDtoUserDtoRoleDto.getUsername(),
//                doctorRoleId);
//
//        return "successful";
//    }
//
//
//    @GetMapping("/administrator/administrator")
//    public String createNewAdministrator(Model model) {
//        model.addAttribute("admin", new HospitalStuffDtoUserDtoRoleDto());
//        return "addAdministrator";
//    }
//
//    @Transactional
//    @RequestMapping(value = "/administrator/administrator", method = RequestMethod.POST, params = "action=save")
//    public String createNewAdministrator(@ModelAttribute("admin") HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto) {
//        int administratorRoleId = 1;
//        String doctorSpecialization = null;
//
//        registrationService.addNewEmployee(
//                hospitalStuffDtoUserDtoRoleDto.getFirstname(),
//                hospitalStuffDtoUserDtoRoleDto.getLastname(),
//                hospitalStuffDtoUserDtoRoleDto.getUsername(),
//                doctorSpecialization,
//                administratorRoleId);
//
//        registrationService.addNewUserToSecurityTable(
//                hospitalStuffDtoUserDtoRoleDto.getUsername(),
//                hospitalStuffDtoUserDtoRoleDto.getPassword());
//
//        registrationService.addUserRoleToSecurityTable(
//                hospitalStuffDtoUserDtoRoleDto.getUsername(),
//                administratorRoleId);
//        return "successful";
//    }
//
//    @GetMapping("/administrator/nurse")
//    public String addNewNurse(Model model) {
//        model.addAttribute("nurse", new HospitalStuffDtoUserDtoRoleDto());
//        return "addNurse";
//    }
//
//    @Transactional
//    @RequestMapping(value = "/administrator/nurse", method = RequestMethod.POST, params = "action=save")
//    public String addNewNurse(@ModelAttribute("nurse") HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto) {
//        int nurseRoleId = 1;
//        String doctorSpecialization = null;
//
//        registrationService.addNewEmployee(
//                hospitalStuffDtoUserDtoRoleDto.getFirstname(),
//                hospitalStuffDtoUserDtoRoleDto.getLastname(),
//                hospitalStuffDtoUserDtoRoleDto.getUsername(),
//                doctorSpecialization,
//                nurseRoleId);
//
//        registrationService.addNewUserToSecurityTable(
//                hospitalStuffDtoUserDtoRoleDto.getUsername(),
//                hospitalStuffDtoUserDtoRoleDto.getPassword());
//
//        registrationService.addUserRoleToSecurityTable(
//                hospitalStuffDtoUserDtoRoleDto.getUsername(),
//                nurseRoleId);
//        return "successful";
//    }
//
//
//    @GetMapping("/")
//    public String getIndexPage() {
//        return "index";
//    }
//
//    @GetMapping("/main")
//    public String getMain() {
//        return "main";
//    }
//
//    @GetMapping("/successful")
//    public String getSuccessful() {
//        return "successful";
//    }
//
//    @GetMapping("/administrator/appoint")
//    public String getAppoint(Model model) {
//        List<HospitalStuffDto> doctorsList = registrationService.getAllDoctors();
//        List<PatientDto> patientsList = registrationService.getAllPatients();
//
//        model.addAttribute("doctors", doctorsList);
//        model.addAttribute("patients", patientsList);
//
//        return "appoint";
//    }
//
//    @Transactional
//    @RequestMapping(value = "/administrator/appoint", method = RequestMethod.POST, params = "action=save")
//    public String getAppoint(@RequestParam("doctor") int doctorId,
//                             @RequestParam("patient") int patientId) {
//
//        registrationService.appointDoctorToPatient(doctorId, patientId);
//
//        return "successful";
//    }
//
//    @GetMapping("/administrator/patients")
//    public String getPatients(Model model) {
//        List<PatientDto> patientDtoList = registrationService.getAllPatients();
//
//        model.addAttribute("patients", patientDtoList);
//
//        return "allPatients";
//    }
//
//    @GetMapping("/administrator/doctors")
//    public String getDoctors(Model model) {
//
//        List<HospitalStuffDto> doctorList = registrationService.getAllDoctors();
//
//        Map<HospitalStuffDto, Integer> doctors1 = registrationService.getAllDoctorsWithPatientQuantity();
//
//        model.addAttribute("doctors", doctors1);
//
//        return "allDoctors";
//    }
//
//
//
////    @GetMapping("/error")
////    public String getError() {
////        return "error";
////    }
//
//    @GetMapping("/doctor/appointment")
//    public String getDoctorAppointment(Model model, Principal principal) {
//        int doctorId = securityService.getEmployeeId(principal);
//        List<PatientDto> patientDtoList = medicalStuffService.getDoctorPatients(doctorId);
//        Appointment.DoctorAppointment[] doctorAppointments = Appointment.DoctorAppointment.values();
//
//        model.addAttribute("appointmentType", doctorAppointments);
//        model.addAttribute("appointments", new AppointmentDto());
//        model.addAttribute("patients", patientDtoList);
//
//        return "addAppointmentDoctor";
//    }
//
//    @Transactional
//    @RequestMapping(value = "/doctor/appointment", method = RequestMethod.POST, params = "action=save")
//    public String getDoctorAppointment(@ModelAttribute("appointments") AppointmentDto appointmentDto,
//                                       @RequestParam("patientIdDoctorAppointment") int patientId,
//                                       @RequestParam("appointmentTypeDoctor") String appointmentType,
//                                       Principal principal) {
//
//        int doctorId = securityService.getEmployeeId(principal);
//
//        medicalStuffService.createAppointment(patientId, doctorId, appointmentType,
//                appointmentDto.getSummary(), appointmentDto.getDate());
//
//        return "successful";
//    }
//
//
//    @GetMapping("/doctor/diagnose")
//    public String getDiagnose(Model model, Principal principal) {
//        int doctorId = getDoctorId(principal);
//        List<PatientDto> patientDtoList = medicalStuffService.getDoctorPatients(doctorId);
//        Appointment.DoctorAppointment[] doctorAppointments = Appointment.DoctorAppointment.values();
//
//        model.addAttribute("appointmentType", doctorAppointments);
//        model.addAttribute("appointments", new AppointmentDto());
//        model.addAttribute("patients", patientDtoList);
//        model.addAttribute("doctorId", doctorId);
//
//        return "createDiagnose";
//    }
//
//    public int getDoctorId(Principal principal) {
//        return securityService.getEmployeeId(principal);
//    }
//
//    @Transactional
//    @RequestMapping(value = "/doctor/diagnose", method = RequestMethod.POST, params = "action=save")
//    public String getDiagnose(@NotBlank(message = "Field summary is empty") @RequestParam("diagnoseSummary") String summary,
//                              @NotNull(message = "Field patient is empty")@RequestParam("patientIdDoctorDiagnose") int patientId,
//                              Principal principal) {
//
//        int doctorId = getDoctorId(principal);
//
//        medicalStuffService.createDiagnose(patientId, doctorId, summary);
//
//        return "successful";
//    }
//
//}
