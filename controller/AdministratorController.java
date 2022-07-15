package com.khmal.hospital.controller;

import com.khmal.hospital.entity.*;
import com.khmal.hospital.exception_handling.NoSuchUserException;
import com.khmal.hospital.request.DoctorPatientRequest;
import com.khmal.hospital.service.*;
import com.khmal.hospital.request.UserDoctorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final UserService userService;
    private final RoleService roleService;
    private final NurseService nurseService;
    private final AdministratorService administratorService;

    @PostMapping("/patient")
    public Patient addNewPatient(@RequestBody User user) {
        Patient patient = new Patient(userService.addNewUser(user));


        //draft, for instance
        try {
            patientService.savePatient(patient);
        } catch (Exception e) {
            throw new NoSuchUserException("Patient not saved");
        }

        roleService.addRole(new Role(user.getUsername(), Patient.ROLE));

        return patient;
    }

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        List<Patient> patientList = patientService.getAllPatients();


        if (patientList == null) {
            throw new NoSuchUserException("Patient list is empty");
        }
        return patientList;
    }

    @PutMapping("/patient")
    public Patient updatePatient(@RequestBody Patient patient) {

        //draft, for instance
        try {
            patientService.savePatient(patient);
        } catch (Exception e) {
            throw new NoSuchUserException("Patient not updated");
        }
        return patient;
    }

    @DeleteMapping("/patient")
    public void deletePatient(@RequestBody Patient patient) {
        patientService.deletePatient(patient);
    }

    @PostMapping("/nurse")
    public Nurse addNewNurse(@RequestBody User user) {
        Nurse nurse = new Nurse(userService.addNewUser(user));

        try {
            nurseService.saveNurse(nurse);
        } catch (Exception e) {
            throw new NoSuchUserException("Nurse not saved");
        }

        roleService.addRole(new Role(user.getUsername(), Nurse.ROLE));

        return nurse;
    }

    @GetMapping("/nurse")
    public List<Nurse> getAllNurses() {
        return nurseService.getAllNurses();
    }

    @PutMapping("/nurse")
    public Nurse updateNurse(@RequestBody Nurse nurse) {
        return nurseService.saveNurse(nurse);
    }

    @DeleteMapping("/nurse")
    public void deleteNurse(@RequestBody Nurse nurse) {
        nurseService.deleteNurse(nurse);
    }


    @PostMapping("/doctor")
    public Doctor addNewDoctor(@RequestBody UserDoctorRequest userDoctorRequest) {

        User user = userService.addNewUser(userDoctorRequest.getUser());

        Doctor doctor = new Doctor(userDoctorRequest.getDoctor()
                .getDoctorSpecialization(), user);

        doctorService.saveDoctor(doctor);
        roleService.addRole(new Role(userDoctorRequest.getUser().getUsername(), Doctor.ROLE));

        return doctor;
    }

    @GetMapping("/doctor")
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctorList = doctorService.getAllDoctors();

        if (doctorList == null) {
            throw new NoSuchUserException("Doctor list is empty");
        }
        return doctorList;
    }

    @PutMapping("/doctor")
    public Doctor updateDoctor(Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    @PutMapping("/doctor/appoint")
    public Doctor appointPatientToTheDoctor(@RequestBody DoctorPatientRequest doctorPatientRequest) {

        Doctor doctor = doctorPatientRequest.getDoctor();
        Patient patient = doctorPatientRequest.getPatient();

        doctor.addPatientToPatientList(patient);
        doctorService.saveDoctor(doctor);

        return doctor;
    }

    @DeleteMapping("/doctor")
    public void deleteDoctor(Doctor doctor) {
        doctorService.deleteDoctor(doctor);
    }

    @PostMapping("/admin")
    public Administrator addNewAdministrator(@RequestBody User user) {
        Administrator administrator = new Administrator(userService.addNewUser(user));

        administratorService.saveAdmin(administrator);
        roleService.addRole(new Role(user.getUsername(), Administrator.ROLE));

        return administrator;
    }

    @GetMapping("/admin")
    public List<Administrator> getAllAdministrators() {
        return administratorService.getAllAdmins();
    }

    @PutMapping("/admin")
    public Administrator updateAdministrator(Administrator administrator) {
        return administratorService.saveAdmin(administrator);
    }

    @DeleteMapping("/admin")
    public void deleteAdministrator(Administrator administrator) {
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
