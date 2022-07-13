package com.khmal.hospital.controller;

import com.khmal.hospital.entity.Appointment;
import com.khmal.hospital.entity.AppointmentType;
import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.Patient;
import com.khmal.hospital.service.*;
import com.khmal.hospital.request.AppointmentDoctorPatientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/nurse")
public class NurseController {
    private final AppointmentServiceImpl appointmentService;
    private final PatientServiceImpl patientService;
    private final DoctorServiceImpl doctorService;
    private final AppointmentTypeServiceImpl appointmentTypeService;

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

    @DeleteMapping("/appointment/{id}")
    public void deleteAppointmentById(@PathVariable Integer id){
         appointmentService.deleteById(id);
    }

    @PutMapping("/appointment")
    public Appointment updateAppointmentById(@RequestBody Appointment appointment){
        appointmentService.saveAppointment(appointment);
        return appointment;
    }

    @GetMapping("/appointment")
    public List<Appointment> getAllAppointments(){
       return appointmentService.getAllAppointments();
    }

    @Autowired
    public NurseController(AppointmentServiceImpl appointmentService, PatientServiceImpl patientService, DoctorServiceImpl doctorService, AppointmentTypeServiceImpl appointmentTypeService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentTypeService = appointmentTypeService;
    }
}
