package com.khmal.hospital.controller;

import com.khmal.hospital.entity.*;
import com.khmal.hospital.service.*;
import com.khmal.hospital.viewmodel.AppointmentDoctorPatient;
import com.khmal.hospital.viewmodel.DiagnoseDoctorPatientViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final AppointmentServiceImpl appointmentService;
    private final DiagnoseServiceImpl diagnoseService;
    private final PatientServiceImpl patientService;
    private final DoctorServiceImpl doctorService;
    private final AppointmentTypeServiceImpl appointmentTypeService;


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

    @Autowired
    public DoctorController(AppointmentServiceImpl appointmentService, DiagnoseServiceImpl diagnoseService, PatientServiceImpl patientService, DoctorServiceImpl doctorService, AppointmentTypeServiceImpl appointmentTypeService) {
        this.appointmentService = appointmentService;
        this.diagnoseService = diagnoseService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentTypeService = appointmentTypeService;
    }
}
