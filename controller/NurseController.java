package com.khmal.hospital.controller;

import com.khmal.hospital.entity.Appointment;
import com.khmal.hospital.entity.AppointmentType;
import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.Patient;
import com.khmal.hospital.service.*;
import com.khmal.hospital.viewmodel.AppointmentDoctorPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequestMapping("/nurse")
public class NurseController {
    private final AppointmentServiceImpl appointmentService;
    private final PatientServiceImpl patientService;
    private final DoctorServiceImpl doctorService;
    private final AppointmentTypeServiceImpl appointmentTypeService;

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

    @Autowired
    public NurseController(AppointmentServiceImpl appointmentService, PatientServiceImpl patientService, DoctorServiceImpl doctorService, AppointmentTypeServiceImpl appointmentTypeService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentTypeService = appointmentTypeService;
    }
}
