package com.khmal.hospital.controller;

import com.khmal.hospital.dto.*;
import com.khmal.hospital.entity.*;
import com.khmal.hospital.exception_handling.NoSuchUserException;
import com.khmal.hospital.mapper.*;
import com.khmal.hospital.request.DoctorPatientRequest;
import com.khmal.hospital.service.*;
import com.khmal.hospital.request.UserDoctorRequest;
import org.mapstruct.Mapper;
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
    public Patient addNewPatient(@RequestBody UserDto userDto) {
        Patient patient = new Patient(userService.addNewUser(UserMapper.INSTANCE.toEntity(userDto)));

        //draft, for instance
        try {
            patientService.savePatient(patient);
        } catch (Exception e) {
            throw new NoSuchUserException("Patient not saved");
        }

        roleService.addRole(new Role(userDto.getUsername(), Patient.ROLE));

        return patient;
    }

    @GetMapping("/patients")
    public List<PatientDto> getAllPatients() {
        List<Patient> patientList = patientService.getAllPatients();

        if (patientList == null) {
            throw new NoSuchUserException("Patient list is empty");
        }

        return PatientMapper.INSTANCE.toDto(patientList);
    }

    @PutMapping("/patient")
    public PatientDto updatePatient(@RequestBody PatientDto patientDto) {

        Patient patient = PatientMapper.INSTANCE.toEntity(patientDto);
        //draft, for instance
        try {
            patientService.savePatient(patient);
        } catch (Exception e) {
            throw new NoSuchUserException("Patient not updated");
        }
        return patientDto;
    }

    @DeleteMapping("/patient")
    public void deletePatient(@RequestBody PatientDto patientDto) {
        patientService.deletePatient(PatientMapper.INSTANCE.toEntity(patientDto));
    }

    @PostMapping("/nurse")
    public NurseDto addNewNurse(@RequestBody UserDto userDto) {
        Nurse nurse = new Nurse(userService.addNewUser(UserMapper.INSTANCE.toEntity(userDto)));

        try {
            nurseService.saveNurse(nurse);
        } catch (Exception e) {
            throw new NoSuchUserException("Nurse not saved");
        }

        roleService.addRole(new Role(userDto.getUsername(), Nurse.ROLE));

        return NurseMapper.INSTANCE.toDto(nurse);
    }

    @GetMapping("/nurse")
    public List<NurseDto> getAllNurses() {
        List<Nurse> nurseList = nurseService.getAllNurses();

        if (nurseList == null){
            throw  new NoSuchUserException("There is no nurse");
        }

        return NurseMapper.INSTANCE.toDto(nurseList);
    }

    @PutMapping("/nurse")
    public NurseDto updateNurse(@RequestBody NurseDto nurseDto) {

        Nurse nurse = NurseMapper.INSTANCE.toEntity(nurseDto);

        try {
            nurseService.saveNurse(nurse);
        }catch (Exception e){
            throw new NoSuchUserException("Nurse wasn't update");
        }

        return nurseDto;
    }

    @DeleteMapping("/nurse")
    public void deleteNurse(@RequestBody NurseDto nurseDto) {

        try {
            nurseService.deleteNurse(NurseMapper.INSTANCE.toEntity(nurseDto));
        }catch (Exception e){
            throw new NoSuchUserException("Nurse wasn't delete");
        }
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
    public void deleteDoctor(DoctorDto doctorDto){

    doctorService.deleteDoctor(DoctorMapper.INSTANCE.toEntity(doctorDto));

    }

    @PostMapping("/admin")
    public Administrator addNewAdministrator(@RequestBody User user) {
        Administrator administrator = new Administrator(userService.addNewUser(user));

        administratorService.saveAdmin(administrator);
        roleService.addRole(new Role(user.getUsername(), Administrator.ROLE));

        return administrator;
    }

    @GetMapping("/admin")
    public List<AdministratorDto> getAllAdministrators() {
        List<Administrator> administratorList = administratorService.getAllAdmins();

        if(administratorList == null){
            throw new NoSuchUserException("There is no administrators");
        }

        return AdministratorMapper.INSTANCE.toDto(administratorService.getAllAdmins());
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
    public AdministratorController(DoctorService doctorService, PatientService patientService, UserService userService, RoleService roleService, NurseService nurseService, AdministratorService administratorService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.userService = userService;
        this.roleService = roleService;
        this.nurseService = nurseService;
        this.administratorService = administratorService;
    }
}
