package com.khmal.hospital.controller;

import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dto.HospitalStuffDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.request.EmployeePatientAppointment;
import com.khmal.hospital.dto.request.HospitalStuffDtoUserDtoRoleDto;
import com.khmal.hospital.dto.request.PatientDtoUserDtoRoleDto;
import com.khmal.hospital.service.MedicalStuffService;
import com.khmal.hospital.service.RegistrationService;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @PostMapping("/patient")
    public PatientDto addNewPatient(@RequestBody PatientDtoUserDtoRoleDto patientDtoUserDtoRoleDto) {

      PatientDto patientDto =  registrationService.addNewPatient(
                patientDtoUserDtoRoleDto.getFirstName(),
                patientDtoUserDtoRoleDto.getLastname(),
                patientDtoUserDtoRoleDto.getUsername(),
                patientDtoUserDtoRoleDto.getBirthday(),
                patientDtoUserDtoRoleDto.isDischarged()
        );

        registrationService.addNewUserToSecurityTable(
                patientDtoUserDtoRoleDto.getUsername(),
                patientDtoUserDtoRoleDto.getPassword(),
                patientDtoUserDtoRoleDto.getEnabled()
        );

        registrationService.addUserRoleToSecurityTable(
                patientDtoUserDtoRoleDto.getUsername(),
                patientDtoUserDtoRoleDto.getRoleId()
        );

        return patientDto;
    }

    @Transactional
    @PostMapping("/employee")
    public HospitalStuffDto addNewEmployee(@RequestBody HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto) {

       HospitalStuffDto hospitalStuffDto = registrationService.addNewEmployee(
                hospitalStuffDtoUserDtoRoleDto.getFirstname(),
                hospitalStuffDtoUserDtoRoleDto.getLastname(),
                hospitalStuffDtoUserDtoRoleDto.getUsername(),
                hospitalStuffDtoUserDtoRoleDto.getDoctorSpecialization(),
                hospitalStuffDtoUserDtoRoleDto.getStuffRoleId()
        );

        registrationService.addNewUserToSecurityTable(
                hospitalStuffDtoUserDtoRoleDto.getUsername(),
                hospitalStuffDtoUserDtoRoleDto.getPassword(),
                hospitalStuffDtoUserDtoRoleDto.getEnabled());

        registrationService.addUserRoleToSecurityTable(
                hospitalStuffDtoUserDtoRoleDto.getUsername(),
                hospitalStuffDtoUserDtoRoleDto.getStuffRoleId());

        return hospitalStuffDto;
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
