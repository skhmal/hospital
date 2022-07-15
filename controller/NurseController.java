package com.khmal.hospital.controller;

import com.khmal.hospital.entity.Appointment;
import com.khmal.hospital.service.*;
import com.khmal.hospital.request.AppointmentDoctorPatientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import static com.khmal.hospital.controller.DoctorController.getAppointment;


@RestController
@RequestMapping("/nurse")
public class NurseController {
    private final AppointmentServiceImpl appointmentService;
    private final PatientServiceImpl patientService;
    private final DoctorServiceImpl doctorService;
    private final AppointmentTypeServiceImpl appointmentTypeService;

    @PostMapping("/appointment")
    public Appointment createAppointment(@RequestBody AppointmentDoctorPatientRequest appointmentDoctorPatientRequest){
        return getAppointment(appointmentDoctorPatientRequest, doctorService, patientService, appointmentTypeService, appointmentService);
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
