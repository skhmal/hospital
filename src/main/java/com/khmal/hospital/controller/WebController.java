package com.khmal.hospital.controller;

import com.khmal.hospital.dao.entity.HospitalStuff;
import com.khmal.hospital.dto.HospitalStuffDto;
import com.khmal.hospital.dto.request.HospitalStuffDtoUserDtoRoleDto;
import com.khmal.hospital.service.RegistrationService;
import com.khmal.hospital.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class WebController {

    private final RegistrationService registrationService;
    private final SecurityService securityService;

    public WebController(RegistrationService registrationService, SecurityService securityService) {
        this.registrationService = registrationService;
        this.securityService = securityService;
    }

    @GetMapping("/p")
    public String getAllPatients(Model model, Principal principal) {
        model.addAttribute("patients", registrationService.getAllPatients());
        model.addAttribute("principal1", principal.getName());
        return "index";
    }

    @GetMapping("/patient/{id}")
    public String showPatientDetails(@PathVariable("id") int id, Model model, Principal principal1) {
        model.addAttribute("details", registrationService.getPatientById(id));
        model.addAttribute("principal1", principal1.getName());
//        model.addAttribute("user", securityService.getUserDetails(principal1));
        return "showUser";
    }

    @GetMapping("/a/{id}")
    public String test(@PathVariable("id") int id, Model model, Principal principal1) {
        model.addAttribute("details", registrationService.getPatientById(id));
        model.addAttribute("principal1", principal1.getName());
//        model.addAttribute("user", securityService.getUserDetails(principal1));
        return "main";
    }

    @GetMapping("/patient")
    public String testAddPatient(Model model, Principal principal1) {
        return "addPatient";
    }

    @GetMapping("/employee")
    public String testAddEmployee(Model model, Principal principal1) {
        model.addAttribute("employee", new HospitalStuffDtoUserDtoRoleDto());
        model.addAttribute("staffRoles", registrationService.getAllStaffRoles());
        model.addAttribute("specializations", HospitalStuff.DoctorSpecialization.values());
        return "addEmployee";
    }

    @Transactional
    @RequestMapping(value = "/employee", method = RequestMethod.POST, params = "action=save")
    public String testAddUser(@ModelAttribute("employee") HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto,
                              @RequestParam(value = "staffRoleSelector") int roleId,
                              @RequestParam(value = "doctorSpecialization") String doctorSpecialization) {

        registrationService.addNewEmployee(
                hospitalStuffDtoUserDtoRoleDto.getFirstname(),
                hospitalStuffDtoUserDtoRoleDto.getLastname(),
                hospitalStuffDtoUserDtoRoleDto.getUsername(),
                doctorSpecialization,
                roleId
        );

        registrationService.addNewUserToSecurityTable(
                hospitalStuffDtoUserDtoRoleDto.getUsername(),
                hospitalStuffDtoUserDtoRoleDto.getPassword());

        registrationService.addUserRoleToSecurityTable(
                hospitalStuffDtoUserDtoRoleDto.getUsername(),
                roleId);

        return "main";
    }


    @GetMapping("/administrator")
    public String testAddAdministrator(Model model) {
        model.addAttribute("admin", new HospitalStuffDtoUserDtoRoleDto());
        return "addAdministrator";
    }

    @RequestMapping(value = "/administrator", method = RequestMethod.POST, params = "action=save")
    public String testAddAdm(@ModelAttribute("admin") HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto) {
        System.out.println("   !!!!  " + hospitalStuffDtoUserDtoRoleDto.getStuffRoleId());
        System.out.println("username = " + hospitalStuffDtoUserDtoRoleDto.getUsername());
        System.out.println("first = " + hospitalStuffDtoUserDtoRoleDto.getFirstname());
        System.out.println("doctorSpec = " + hospitalStuffDtoUserDtoRoleDto.getDoctorSpecialization());
        HospitalStuffDto hospitalStuffDto = registrationService.addNewEmployee(
                hospitalStuffDtoUserDtoRoleDto.getFirstname(),
                hospitalStuffDtoUserDtoRoleDto.getLastname(),
                hospitalStuffDtoUserDtoRoleDto.getUsername(),
                hospitalStuffDtoUserDtoRoleDto.getDoctorSpecialization(),
                hospitalStuffDtoUserDtoRoleDto.getStuffRoleId()
        );

        registrationService.addNewUserToSecurityTable(
                hospitalStuffDtoUserDtoRoleDto.getUsername(),
                hospitalStuffDtoUserDtoRoleDto.getPassword());

        registrationService.addUserRoleToSecurityTable(
                hospitalStuffDtoUserDtoRoleDto.getUsername(),
                hospitalStuffDtoUserDtoRoleDto.getStuffRoleId());

        return "main";
    }
}
