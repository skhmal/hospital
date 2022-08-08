package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.Diagnose;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.DiagnoseRepository;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.DiagnoseDto;
import com.khmal.hospital.mapper.AppointmentMapper;
import com.khmal.hospital.mapper.DiagnoseMapper;
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final AppointmentRepository appointmentRepository;
    private final DiagnoseRepository diagnoseRepository;

    public PatientService(AppointmentRepository appointmentRepository, DiagnoseRepository diagnoseRepository) {
        this.appointmentRepository = appointmentRepository;
        this.diagnoseRepository = diagnoseRepository;
    }

    public List<AppointmentDto> getPatientAppointments(int id){
        List<Appointment> appointmentList = appointmentRepository.findAppointmentByPatientId(id).orElseThrow(
                () -> new IncorrectDateException("Appointment list for user is empty!"));
        return AppointmentMapper.INSTANCE.toDto(appointmentList);
    }

    public List<DiagnoseDto> getPatientDiagnoses(int id){
        List<Diagnose> diagnoseList = diagnoseRepository.getDiagnoseByPatientId(id).orElseThrow(
                () -> new IncorrectDateException("Diagnose list for user is empty!"));
        return DiagnoseMapper.INSTANCE.toDto(diagnoseList);
    }
}
