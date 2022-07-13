package com.khmal.hospital.controller;

import com.khmal.hospital.entity.*;
import com.khmal.hospital.service.*;
import com.khmal.hospital.request.AppointmentDoctorPatientRequest;
import com.khmal.hospital.request.DiagnoseDoctorPatientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final AppointmentServiceImpl appointmentService;
    private final DiagnoseServiceImpl diagnoseService;
    private final PatientServiceImpl patientService;
    private final DoctorServiceImpl doctorService;
    private final AppointmentTypeServiceImpl appointmentTypeService;


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

    @GetMapping("/diagnose")
    public List<Diagnose> getAllDiagnoses(){
        return diagnoseService.getAllDiagnoses();
    }

    @PutMapping("/diagnose")
    public Diagnose updateDiagnose(@RequestBody Diagnose diagnose){
        diagnoseService.saveDiagnose(diagnose);
        return diagnose;
    }

    @DeleteMapping("/diagnose")
    public void deleteDiagnose(@RequestBody Diagnose diagnose){
        diagnoseService.deleteDiagnose(diagnose);
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

    @GetMapping("/appointment")
    public List<Appointment> getAllAppointments(){
       return appointmentService.getAllAppointments();
    }

    @PutMapping("/appointment")
    public Appointment updateAppointments(@RequestBody Appointment appointment){
        return appointmentService.saveAppointment(appointment);
    }

    @DeleteMapping("/appointment")
    public void deleteAppointment(Appointment appointment){
        appointmentService.deleteAppointment(appointment);
    }

    @Autowired
    public DoctorController(AppointmentServiceImpl appointmentService, DiagnoseServiceImpl diagnoseService, PatientServiceImpl patientService, DoctorServiceImpl doctorService, AppointmentTypeServiceImpl appointmentTypeService) {
        this.appointmentService = appointmentService;
        this.diagnoseService = diagnoseService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentTypeService = appointmentTypeService;
    }
}
