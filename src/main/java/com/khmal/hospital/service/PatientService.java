package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.Appointment;
import com.khmal.hospital.dao.entity.Diagnose;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.DiagnoseRepository;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.DiagnoseDto;
import com.khmal.hospital.mapper.AppointmentPaginationMapper;
import com.khmal.hospital.mapper.DiagnosePaginationMapper;
import com.khmal.hospital.service.exception_handling.IncorrectDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service class for patient.
 * - get all diagnoses for particular patient (with pagination).
 * - get all appointments for particular patient (with pagination)
 */
@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    private final AppointmentRepository appointmentRepository;
    private final DiagnoseRepository diagnoseRepository;

    public PatientService(AppointmentRepository appointmentRepository, DiagnoseRepository diagnoseRepository) {
        this.appointmentRepository = appointmentRepository;
        this.diagnoseRepository = diagnoseRepository;
    }

    public Page<AppointmentDto> getAllPatientAppointmentsPaginated(int pageNo, int pageSize, String sortField,
                                                                   String sortDirection,
                                                                   int patientId) {

        logger.info("Method getAllPatientAppointmentsPaginated started");

        Sort sort = getSort(sortField,
                sortDirection);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Appointment> appointmentPage = appointmentRepository.findAppointmentByPatientId(patientId, pageable)
                .orElseThrow(() -> new IncorrectDataException("There is no appointment!"));

        Page<AppointmentDto> appointmentDto = AppointmentPaginationMapper.toDto(appointmentPage);

        if (appointmentDto.getContent().isEmpty()) {
            logger.info("The Patient doesn't have appointments ");
            throw new IncorrectDataException("There is no appointment");
        }

        logger.info("Method getAllPatientAppointmentsPaginated finished");
        return appointmentDto;
    }


    public Page<DiagnoseDto> getAllPatientDiagnosesPaginated(int pageNo, int pageSize, String sortField,
                                                             String sortDirection, int patientId) {
        logger.info("Method getAllPatientDiagnosesPaginated started");
        Sort sort = getSort(sortField,
                sortDirection);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Diagnose> diagnosePage = diagnoseRepository.findDiagnoseByPatientId(patientId, pageable)
                .orElseThrow(() -> new IncorrectDataException("There is no diagnose!"));

        Page<DiagnoseDto> diagnoseDtoPage = DiagnosePaginationMapper.toDto(diagnosePage);

        if (diagnoseDtoPage.getContent().isEmpty()) {
            logger.info("The Patient doesn't have diagnoses");
            throw new IncorrectDataException("There is no diagnose");
        }

        logger.info("Method getAllPatientDiagnosesPaginated finished");
        return diagnoseDtoPage;
    }

    /**
     * Method to choose sort direction (asc or desc).
     * @param sortField field to sort in table.
     * @param sortDirection sort direction
     * @return
     */
    public Sort getSort(String sortField,
                        String sortDirection) {
        return sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
    }
}
