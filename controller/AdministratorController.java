package com.khmal.hospital.controller;

import com.khmal.hospital.entity.*;
import com.khmal.hospital.service.*;
import com.khmal.hospital.viewmodel.UserDoctorViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {

    private DoctorServiceImpl doctorService;
    private PatientServiceImpl patientService;
    private UserServiceImpl userService;
    private RoleServiceImpl roleService;
    private NurseServiceImpl nurseService;
    private AdministratorServiceImpl administratorService;



    @PostMapping("/newpatient")
    public Patient addNewPatient(@RequestBody User user){
        Patient patient = new Patient(userService.addNewUser(user));

        patientService.addNewPatient(patient);
        roleService.addRole(new Role(user.getUsername(), Patient.ROLE));

        return patient;
    }

    @PostMapping("/newnurse")
    public Nurse addNewNurse(@RequestBody User user){
        Nurse nurse = new Nurse(userService.addNewUser(user));

        nurseService.addNewNurse(nurse);
        roleService.addRole(new Role(user.getUsername(), Nurse.ROLE));

        return nurse;
    }

    @PostMapping("/newdoctor")
    public Doctor addNewDoctor(@RequestBody UserDoctorViewModel userDoctorViewModel){

        User user = userService.addNewUser(userDoctorViewModel.getUser());

        Doctor doctor = new Doctor(userDoctorViewModel.getDoctor()
                .getDoctorSpecialization(), user);

        doctorService.addDoctor(doctor);
        roleService.addRole(new Role(userDoctorViewModel.getUser().getUsername(), Doctor.ROLE));

        return doctor;
    }

    @PostMapping("/newadmin")
    public Administrator addNewAdministrator(@RequestBody User user){
        Administrator administrator = new Administrator(userService.addNewUser(user));

        administratorService.addNewAdministrator(administrator);
        roleService.addRole(new Role(user.getUsername(), Administrator.ROLE));

        return administrator;
    }

    @Autowired
    public AdministratorController(DoctorServiceImpl doctorService, PatientServiceImpl patientService, UserServiceImpl userService, RoleServiceImpl roleService, NurseServiceImpl nurseService, AdministratorServiceImpl administratorService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.userService = userService;
        this.roleService = roleService;
        this.nurseService = nurseService;
        this.administratorService = administratorService;
    }
}
