package com.khmal.hospital.controller;

import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.Patient;
import com.khmal.hospital.entity.User;
import com.khmal.hospital.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/")
public class RestController {

    @Autowired
    public RestController(AdministratorServiceImpl administratorService, PatientServiceImpl patientService, DoctorServiceImpl doctorService, UserService userService) {
        this.administratorService = administratorService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.userService = userService;
    }

    public RestController() {
    }

    private AdministratorServiceImpl administratorService;
    private PatientServiceImpl patientService;

    private DoctorServiceImpl doctorService;

    private UserService userService;

   @PostMapping("/patient")
    public Patient addNewPatient(@RequestBody User user){
       Patient patient = new Patient(userService.addNewUser(user));
//       patient.getPermission();
       patientService.addNewPatient(patient);
       return patient;
    }

    @GetMapping("/patient/{id}")
    public Patient getPatientById(@PathVariable Integer id){
        System.out.println("get patient");
        Patient patient = patientService.getPatientById(id);
        return patient;
    }

//    @GetMapping("/patient/{name}")
//    public Patient getPatientByName(@PathVariable String name){
//        Patient patient = patientService.getPatientByName(name);
//        return patient;
//    }

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

//    @GetMapping("/doctor/{name}")
//    public Doctor getDoctorByName(@PathVariable("name") String name){
//        Doctor doctor = doctorService.getDoctorByFirstName(name);
//        return doctor;
//    }
}
