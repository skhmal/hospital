package com.khmal.hospital.controller;

import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dto.HospitalStuffDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.request.EmployeePatientAppointment;
import com.khmal.hospital.dto.request.HospitalStuffDtoUserDtoRoleDto;
import com.khmal.hospital.dto.request.PatientDtoUserDtoRoleDto;
import com.khmal.hospital.service.MedicalStuffService;
import com.khmal.hospital.service.RegistrationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    private final RegistrationService registrationService;
    private final MedicalStuffService appointmentService;

    public AdministratorController(RegistrationService registrationService, MedicalStuffService appointmentService) {
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

    @PostMapping("/appoint")
    public void appointDoctorToPatient(@RequestBody EmployeePatientAppointment employeePatientAppointment){
        registrationService.appointDoctorToPatient(
                employeePatientAppointment.getHospitalStuffId(),
                employeePatientAppointment.getPatientId());
    }
    @GetMapping("/patients")
    public List<PatientDto> getAllPatients() {
        return registrationService.getAllPatients();
    }

    @GetMapping("/doctors")
    public List<HospitalStuffDto> getAllDoctors() {
        return registrationService.getAllDoctors();
    }

    @GetMapping("/patients/{id}")
    public List<Patient> getDoctorsPatientListById(@PathVariable("id") int id){
      return   appointmentService.getDoctorsPatientListById(id);
    }

}
