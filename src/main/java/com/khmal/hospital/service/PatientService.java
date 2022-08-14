package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.Diagnose;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.DiagnoseRepository;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.DiagnoseDto;
import com.khmal.hospital.mapper.AppointmentPaginationMapper;
import com.khmal.hospital.mapper.DiagnosePaginationMapper;
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
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
//        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
//                Sort.by(sortField).descending();

       Sort sort =  getSort( sortField,
                 sortDirection);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Appointment> appointmentPage = appointmentRepository.findAppointmentByPatientId(patientId, pageable)
                .orElseThrow( () -> new IncorrectDateException("There is no appointment!"));

        Page<AppointmentDto> appointmentDto = AppointmentPaginationMapper.toDto(appointmentPage);

        if (appointmentDto.getContent().size() < 1) {
            throw new IncorrectDateException("There is no appointment");
        }

        return appointmentDto;
    }

    public Page<DiagnoseDto> getAllPatientDiagnosesPaginated(int pageNo, int pageSize, String sortField,
                                                             String sortDirection, int patientId) {

        Sort sort =  getSort( sortField,
                sortDirection);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Diagnose> diagnosePage = diagnoseRepository.findDiagnoseByPatientId(patientId, pageable)
                .orElseThrow(() -> new IncorrectDateException("There is no diagnose!"));

        Page<DiagnoseDto> diagnoseDtoPage = DiagnosePaginationMapper.toDto(diagnosePage);

        if (diagnoseDtoPage.getContent().size() < 1) {
            throw new IncorrectDateException("There is no diagnose");
        }

        return diagnoseDtoPage;
    }

    public Sort getSort(String sortField,
                        String sortDirection){
        return sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
    }
}
