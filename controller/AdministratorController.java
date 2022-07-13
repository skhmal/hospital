package com.khmal.hospital.controller;

import com.khmal.hospital.entity.*;
import com.khmal.hospital.service.*;
import com.khmal.hospital.request.UserDoctorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {

    private final DoctorServiceImpl doctorService;
    private final PatientServiceImpl patientService;
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final NurseServiceImpl nurseService;
    private final AdministratorServiceImpl administratorService;

    @PostMapping("/patient")
    public Patient addNewPatient(@RequestBody User user){
        Patient patient = new Patient(userService.addNewUser(user));

        patientService.savePatient(patient);
        roleService.addRole(new Role(user.getUsername(), Patient.ROLE));

        return patient;
    }

    @GetMapping("/patients")
    public List<Patient> getAllPatients(){
       return patientService.getAllPatients();
    }

    @PutMapping("/patient")
    public Patient updatePatient(@RequestBody Patient patient){
        patientService.savePatient(patient);
        return patient;
    }

    @DeleteMapping("/patient")
    public void deletePatient(@RequestBody Patient patient){
        patientService.deletePatient(patient);
    }

    @PostMapping("/nurse")
    public Nurse addNewNurse(@RequestBody User user){
        Nurse nurse = new Nurse(userService.addNewUser(user));

        nurseService.saveNurse(nurse);
        roleService.addRole(new Role(user.getUsername(), Nurse.ROLE));

        return nurse;
    }

    @GetMapping("/nurse")
    public List<Nurse> getAllNurses(){
      return nurseService.getAllNurses();
    }

    @PutMapping("/nurse")
    public Nurse updateNurse(@RequestBody Nurse nurse){
       return nurseService.saveNurse(nurse);
    }

    @DeleteMapping("/nurse")
    public void deleteNurse(@RequestBody Nurse nurse){
        nurseService.deleteNurse(nurse);
    }


    @PostMapping("/doctor")
    public Doctor addNewDoctor(@RequestBody UserDoctorRequest userDoctorRequest){

        User user = userService.addNewUser(userDoctorRequest.getUser());

        Doctor doctor = new Doctor(userDoctorRequest.getDoctor()
                .getDoctorSpecialization(), user);

        doctorService.saveDoctor(doctor);
        roleService.addRole(new Role(userDoctorRequest.getUser().getUsername(), Doctor.ROLE));

        return doctor;
    }

    @GetMapping("/doctor")
    public List<Doctor> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @PutMapping("/doctor")
    public Doctor updateDoctor(Doctor doctor){
     return doctorService.saveDoctor(doctor);
    }

    @DeleteMapping("/doctor")
    public void deleteDoctor(Doctor doctor){
        doctorService.deleteDoctor(doctor);
    }

    @PostMapping("/admin")
    public Administrator addNewAdministrator(@RequestBody User user){
        Administrator administrator = new Administrator(userService.addNewUser(user));

        administratorService.saveAdmin(administrator);
        roleService.addRole(new Role(user.getUsername(), Administrator.ROLE));

        return administrator;
    }

    @GetMapping("/admin")
    public List<Administrator> getAllAdministrators(){
       return administratorService.getAllAdmins();
    }

    @PutMapping("/admin")
    public Administrator updateAdministrator(Administrator administrator){
        return administratorService.saveAdmin(administrator);
    }

    @DeleteMapping("/admin")
    public void deleteAdministrator(Administrator administrator){
        administratorService.deleteAdmin(administrator);
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
