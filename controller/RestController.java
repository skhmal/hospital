package com.khmal.hospital.controller;

import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.Patient;
import com.khmal.hospital.service.AdministratorServiceImpl;
import com.khmal.hospital.service.DoctorServiceImpl;
import com.khmal.hospital.service.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/")
public class RestController {

    @Autowired
    public RestController(AdministratorServiceImpl administratorService, PatientServiceImpl patientService, DoctorServiceImpl
                          doctorService) {
        this.administratorService = administratorService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public RestController() {
    }

    private AdministratorServiceImpl administratorService;
    private PatientServiceImpl patientService;

    private DoctorServiceImpl doctorService;

    @PostMapping("/patient")
    public Patient addNewPatient(@RequestBody Patient patient){
        administratorService.addPatient(patient);
        return patient;
    }

    @GetMapping("/patient/{id}")
    public Patient getPatientById(@PathVariable Integer id){
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

    @RequestMapping(method = RequestMethod.GET)
    public Patient getPatientByName(@RequestParam(value = "name") String name){
        Patient patient = patientService.getPatientByName(name);
        return patient;
    }
}
