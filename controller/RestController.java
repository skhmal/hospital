package com.khmal.hospital.controller;

import com.khmal.hospital.entity.*;
import com.khmal.hospital.service.*;
import com.khmal.hospital.viewmodel.AppointmentDoctorPatient;
import com.khmal.hospital.viewmodel.DiagnoseDoctorPatientViewModel;
import com.khmal.hospital.viewmodel.UserDoctorViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {


    @Autowired
    public RestController(AdministratorServiceImpl administratorService, NurseServiceImpl nurseService, PatientServiceImpl patientService, DoctorServiceImpl doctorService, UserServiceImpl userService, RoleServiceImpl roleService, DoctorSpecializationServiceImpl doctorSpecializationService, AppointmentService appointmentService, AppointmentTypeServiceImpl appointmentTypeService, DiagnoseServiceImpl diagnoseService) {
        this.administratorService = administratorService;
        this.nurseService = nurseService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.userService = userService;
        this.roleService = roleService;
        this.doctorSpecializationService = doctorSpecializationService;
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

    private DoctorSpecializationServiceImpl doctorSpecializationService;

    private AppointmentService appointmentService;

    private AppointmentTypeServiceImpl appointmentTypeService;

    private DiagnoseServiceImpl diagnoseService;

    @PostMapping("/patient")
    public Patient addNewPatient(@RequestBody User user){
       Patient patient = new Patient(userService.addNewUser(user));

       patientService.addNewPatient(patient);
       roleService.addRole(new Role(user.getUsername(), Patient.ROLE));

       return patient;
    }

    @PostMapping("/diagnose")
    public Diagnose addNewDiagnose(@RequestBody DiagnoseDoctorPatientViewModel diagnoseDoctorPatientViewModel){

        LocalDate date = LocalDate.now();

        Patient patient = patientService.getPatientById(diagnoseDoctorPatientViewModel.getPatient().getId());

        Doctor doctor = doctorService.getDoctorById(diagnoseDoctorPatientViewModel.getDoctor().getId());

        String summary = diagnoseDoctorPatientViewModel.getSummary();

        Diagnose diagnose = new Diagnose(summary, date, patient, doctor);

        diagnoseService.addNewDiagnose(diagnose);

        return diagnose;
    }

    @PostMapping("/appointment")
    public Appointment createAppointment(@RequestBody AppointmentDoctorPatient appointmentDoctorPatient){

        LocalDate date = LocalDate.now();

        Doctor doctor = doctorService.getDoctorById(appointmentDoctorPatient.getDoctor().getId());

        Patient patient = patientService.getPatientById(appointmentDoctorPatient.getPatient().getId());

        AppointmentType appointmentType = appointmentTypeService.getAppoitmentTypeById(appointmentDoctorPatient.getAppointmentType().getId());

        Appointment appointment = new Appointment(date, appointmentType, patient, doctor);

        appointmentService.addNewAppointment(appointment);

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
