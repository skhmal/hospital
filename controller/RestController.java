package com.khmal.hospital.controller;

import com.khmal.hospital.entity.*;
import com.khmal.hospital.service.*;
import com.khmal.hospital.viewmodel.UserDoctorViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/")
public class RestController {

    @Autowired
    public RestController(AdministratorServiceImpl administratorService, NurseServiceImpl nurseService, PatientServiceImpl patientService, DoctorServiceImpl doctorService, UserServiceImpl userService, RoleServiceImpl roleService, DoctorSpecializationServiceImpl doctorSpecializationService) {
        this.administratorService = administratorService;
        this.nurseService = nurseService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.userService = userService;
        this.roleService = roleService;
        this.doctorSpecializationService = doctorSpecializationService;
    }




    public RestController() {
    }

    private AdministratorServiceImpl administratorService;

    private NurseServiceImpl nurseService;

    private PatientServiceImpl patientService;

    private DoctorServiceImpl doctorService;

    private UserServiceImpl userService;

    private RoleServiceImpl roleService;

    private DoctorSpecializationServiceImpl doctorSpecializationService;

    @PostMapping("/patient")
    public Patient addNewPatient(@RequestBody User user){
       Patient patient = new Patient(userService.addNewUser(user));

       patientService.addNewPatient(patient);
       roleService.addRole(new Role(user.getUsername(), Patient.ROLE));

       return patient;
    }

    @PostMapping("/nurse")
    public Nurse addNewNurse(@RequestBody User user){
        Nurse nurse = new Nurse(userService.addNewUser(user));

        nurseService.addNewNurse(nurse);
        roleService.addRole(new Role(user.getUsername(), Nurse.ROLE));

        return nurse;
    }

    @PostMapping("/doctor")
    public Doctor addNewDoctor(@RequestBody UserDoctorViewModel userDoctorViewModel){

        User user = userService.addNewUser(userDoctorViewModel.getUser());

        Doctor doctor = new Doctor(userDoctorViewModel.getDoctor()
                .getDoctorSpecialization(), user);

        doctorService.addDoctor(doctor);
        roleService.addRole(new Role(userDoctorViewModel.getUser().getUsername(), Doctor.ROLE));

        return doctor;
    }

    @PostMapping("/administrator")
    public Administrator addNewAdministrator(@RequestBody User user){
        Administrator administrator = new Administrator(userService.addNewUser(user));

        administratorService.addNewAdministrator(administrator);
        roleService.addRole(new Role(user.getUsername(), Administrator.ROLE));

        return administrator;
    }

    @GetMapping("/patient/{id}")
    public Patient getPatientById(@PathVariable Integer id){
        System.out.println("get patient");
        Patient patient = patientService.getPatientById(id);
        return patient;
    }

    @GetMapping("/patient")
    public List<Patient> getAllPatient(){
        return patientService.getAllPatients();
    }

    @GetMapping("/doctor/{id}")
    public Doctor getDoctorById(@PathVariable Integer id){
        Doctor doctor = doctorService.getDoctorById(id);
        return doctor;
    }

    @GetMapping("/doctor")
    public List<Doctor> getAllDoctors(){
        return doctorService.getAllDoctors();
    }
}
