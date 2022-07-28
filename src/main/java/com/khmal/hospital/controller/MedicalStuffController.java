package com.khmal.hospital.controller;

import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.DiagnoseDto;
import com.khmal.hospital.dto.request.EmployeePatientAppointment;
import com.khmal.hospital.service.MedicalStuffService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medic")
public class MedicalStuffController {

    private final MedicalStuffService medicalStuffService;

    public MedicalStuffController(MedicalStuffService medicalStuffService) {
        this.medicalStuffService = medicalStuffService;
    }

    @PostMapping("/appointment")
    public AppointmentDto appointmentForPatient(@RequestBody EmployeePatientAppointment employeePatientAppointment) {
        return medicalStuffService.createAppointment(
                employeePatientAppointment.getPatientId(),
                employeePatientAppointment.getHospitalStuffId(),
                employeePatientAppointment.getAppointmentType(),
                employeePatientAppointment.getSummary(),
                employeePatientAppointment.getAppointmentDate());
    }

    @PostMapping("/diagnose")
    public DiagnoseDto addDiagnose(@RequestBody EmployeePatientAppointment employeePatientAppointment) {
        return medicalStuffService.addDiagnose(
                employeePatientAppointment.getPatientId(),
                employeePatientAppointment.getHospitalStuffId(),
                employeePatientAppointment.getSummary()
        );
    }

    @GetMapping("/diagnose/{id}")
    public List<DiagnoseDto> getPatientDiagnoses(@PathVariable("id") int id) {
        return medicalStuffService.getPatientDiagnoses(id);
    }
}
