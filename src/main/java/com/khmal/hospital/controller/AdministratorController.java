package com.khmal.hospital.controller;

import com.khmal.hospital.dto.HospitalStuffDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.request.EmployeePatientAppointment;
import com.khmal.hospital.dto.request.HospitalStuffDtoUserDtoRoleDto;
import com.khmal.hospital.dto.request.PatientDtoUserDtoRoleDto;
import com.khmal.hospital.service.AppointmentService;
import com.khmal.hospital.service.RegistrationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    private final RegistrationService registrationService;
    private final AppointmentService appointmentService;

    public AdministratorController(RegistrationService registrationService, AppointmentService appointmentService) {
        this.registrationService = registrationService;
        this.appointmentService = appointmentService;
    }

    @PostMapping("/patient")
    public PatientDto addNewPatient(@RequestBody PatientDtoUserDtoRoleDto patientDtoUserDtoRoleDto) {

        registrationService.addNewPatient(patientDtoUserDtoRoleDto.getPatientDto());

        registrationService.addNewUserToSecurityTable(patientDtoUserDtoRoleDto.getUserDto());

        registrationService.addUserRoleToSecurityTable(patientDtoUserDtoRoleDto.getRoleDto());

        return patientDtoUserDtoRoleDto.getPatientDto();
    }

    @PostMapping("/employee")
    public HospitalStuffDto addNewEmployee(@RequestBody HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto) {

        registrationService.addNewEmployee(hospitalStuffDtoUserDtoRoleDto.getHospitalStuffDto());

        registrationService.addNewUserToSecurityTable(hospitalStuffDtoUserDtoRoleDto.getUserDto());

        registrationService.addUserRoleToSecurityTable(hospitalStuffDtoUserDtoRoleDto.getRoleDto());

        return hospitalStuffDtoUserDtoRoleDto.getHospitalStuffDto();
    }

    @GetMapping("/patients")
    public List<PatientDto> getAllPatients() {
        return registrationService.getAllPatients();
    }

    @GetMapping("/doctors")
    public List<HospitalStuffDto> getAllDoctors() {
        return registrationService.getAllDoctors();
    }

    @PostMapping("/appointment")
    public void appointmentForPatient(@RequestBody EmployeePatientAppointment employeePatientAppointment){
        appointmentService.createAppointment(
                employeePatientAppointment.getPatientId(),
                employeePatientAppointment.getHospitalStuffId(),
                employeePatientAppointment.getAppointmentType(),
                employeePatientAppointment.getSummary());
    }
}
