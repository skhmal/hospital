package com.khmal.hospital.controller;

import com.khmal.hospital.entity.Appointment;
import com.khmal.hospital.entity.Diagnose;
import com.khmal.hospital.service.AppointmentServiceImpl;
import com.khmal.hospital.service.DiagnoseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final AppointmentServiceImpl appointmentService;
    private final DiagnoseServiceImpl diagnoseService;


    @GetMapping("/appointment")
    public List<Appointment> getAllAppointments(){
       return appointmentService.getAllAppointments();
    }

    @GetMapping("/diagnoses")
    public List<Diagnose> getAllDiagnoses(){
        return diagnoseService.getAllDiagnoses();
    }

    @Autowired
    public PatientController(AppointmentServiceImpl appointmentService, DiagnoseServiceImpl diagnoseService) {
        this.appointmentService = appointmentService;
        this.diagnoseService = diagnoseService;
    }
}
