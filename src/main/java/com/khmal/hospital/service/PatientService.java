package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.Diagnose;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.DiagnoseRepository;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.DiagnoseDto;
import com.khmal.hospital.mapper.AppointmentPaginationMapper;
import com.khmal.hospital.mapper.DiagnosePaginationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final AppointmentRepository appointmentRepository;
    private final DiagnoseRepository diagnoseRepository;

    public PatientService(AppointmentRepository appointmentRepository, DiagnoseRepository diagnoseRepository) {
        this.appointmentRepository = appointmentRepository;
        this.diagnoseRepository = diagnoseRepository;
    }

    public Page<AppointmentDto> getAllPatientAppointmentsPaginated(int pageNo, int pageSize, String sortField,
                                                                   String sortDirection,
                                                                   int patientId) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Appointment> appointmentPage = appointmentRepository.findAppointmentByPatientId(patientId, pageable);

        Page<AppointmentDto> appointmentDto = AppointmentPaginationMapper.toDto(appointmentPage);

        return appointmentDto;
    }

    public Page<DiagnoseDto> getAllPatientDiagnosesPaginated(int pageNo, int pageSize, String sortField,
                                                             String sortDirection, int patientId) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Diagnose> diagnosePage = diagnoseRepository.findDiagnoseByPatientId(patientId, pageable);

        Page<DiagnoseDto> appointmentDto = DiagnosePaginationMapper.toDto(diagnosePage);

        return appointmentDto;
    }
}
