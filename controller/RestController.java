package com.khmal.hospital.controller;

import com.khmal.hospital.entity.*;
import com.khmal.hospital.service.*;
import com.khmal.hospital.request.AppointmentDoctorPatientRequest;
import com.khmal.hospital.request.DiagnoseDoctorPatientRequest;
import com.khmal.hospital.request.UserDoctorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {


    @Autowired
    public RestController(AdministratorServiceImpl administratorService, NurseServiceImpl nurseService, PatientServiceImpl patientService, DoctorServiceImpl doctorService, UserServiceImpl userService, RoleServiceImpl roleService, AppointmentService appointmentService, AppointmentTypeServiceImpl appointmentTypeService, DiagnoseServiceImpl diagnoseService) {
        this.administratorService = administratorService;
        this.nurseService = nurseService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.userService = userService;
        this.roleService = roleService;
        this.appointmentService = appointmentService;
        this.appointmentTypeService = appointmentTypeService;
        this.diagnoseService = diagnoseService;
    }

    public RestController() {
    }

    private AdministratorServiceImpl administratorService;

    private NurseServiceImpl nurseService;

    private PatientServiceImpl patientService;

    private DoctorServiceImpl doctorService;

    private UserServiceImpl userService;

    private RoleServiceImpl roleService;

    private AppointmentService appointmentService;

    private AppointmentTypeServiceImpl appointmentTypeService;

    private DiagnoseServiceImpl diagnoseService;

    @PostMapping("/patient")
    public Patient addNewPatient(@RequestBody User user){
       Patient patient = new Patient(userService.addNewUser(user));

       patientService.savePatient(patient);
       roleService.addRole(new Role(user.getUsername(), Patient.ROLE));

       return patient;
    }

    @PostMapping("/diagnose")
    public Diagnose addNewDiagnose(@RequestBody DiagnoseDoctorPatientRequest diagnoseDoctorPatientViewModel){

        LocalDate date = LocalDate.now();

        Patient patient = patientService.getPatientById(diagnoseDoctorPatientViewModel.getPatient().getId());

        Doctor doctor = doctorService.getDoctorById(diagnoseDoctorPatientViewModel.getDoctor().getId());

        String summary = diagnoseDoctorPatientViewModel.getSummary();

        Diagnose diagnose = new Diagnose(summary, date, patient, doctor);

        diagnoseService.saveDiagnose(diagnose);

        return diagnose;
    }

    @PostMapping("/appointment")
    public Appointment createAppointment(@RequestBody AppointmentDoctorPatientRequest appointmentDoctorPatientRequest){

        LocalDate date = LocalDate.now();

        Doctor doctor = doctorService.getDoctorById(appointmentDoctorPatientRequest.getDoctor().getId());

        Patient patient = patientService.getPatientById(appointmentDoctorPatientRequest.getPatient().getId());

        AppointmentType appointmentType = appointmentTypeService.getAppoitmentTypeById(appointmentDoctorPatientRequest.getAppointmentType().getId());

        Appointment appointment = new Appointment(date, appointmentType, patient, doctor);

        appointmentService.saveAppointment(appointment);

        return appointment;
    }

    @GetMapping("/appointments/{id}")
    public List<Appointment> getPatientAppoitment(@PathVariable Integer id){
        List<Appointment> appointmentList = appointmentService.getPatientAppointments(id);
        return appointmentList;
    }

    @PostMapping("/nurse")
    public Nurse addNewNurse(@RequestBody User user){
        Nurse nurse = new Nurse(userService.addNewUser(user));

        nurseService.saveNurse(nurse);
        roleService.addRole(new Role(user.getUsername(), Nurse.ROLE));

        return nurse;
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

    @PostMapping("/administrator")
    public Administrator addNewAdministrator(@RequestBody User user){
        Administrator administrator = new Administrator(userService.addNewUser(user));

        administratorService.saveAdmin(administrator);
        roleService.addRole(new Role(user.getUsername(), Administrator.ROLE));

        return administrator;
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
}
