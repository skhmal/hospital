package com.khmal.hospital.controller;

import com.khmal.hospital.entity.Appointment;
import com.khmal.hospital.entity.AppointmentType;
import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.Patient;
import com.khmal.hospital.service.AppointmentServiceImpl;
import com.khmal.hospital.service.DoctorServiceImpl;
import com.khmal.hospital.service.PatientServiceImpl;
import com.khmal.hospital.viewmodel.AppointmentDoctorPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private DoctorServiceImpl doctorService;
    private PatientServiceImpl patientService;
    private AppointmentServiceImpl appointmentService;

    @Autowired
    public AppointmentController(DoctorServiceImpl doctorService, PatientServiceImpl patientService, AppointmentServiceImpl appointmentService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    public AppointmentController() {
    }

    @PostMapping("/appointment")
    public Appointment createAppointment(@RequestBody AppointmentDoctorPatient appointmentDoctorPatient){

        LocalDate date = LocalDate.now();

        Doctor doctor = appointmentDoctorPatient.getDoctor();

        Patient patient = appointmentDoctorPatient.getPatient();

        AppointmentType appointmentType = appointmentDoctorPatient.getAppointmentType();

        Appointment appointment = new Appointment(date, appointmentType, patient, doctor);

        appointmentService.addNewAppointment(appointment);

        return appointment;
    }
}
