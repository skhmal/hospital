package com.khmal.hospital.service;

import com.khmal.hospital.controller.exception.handling.IncorrectDataException;
import com.khmal.hospital.dao.entity.*;
import com.khmal.hospital.dao.repository.AppointmentRepository;
import com.khmal.hospital.dao.repository.DiagnoseRepository;
import com.khmal.hospital.dto.AppointmentDto;
import com.khmal.hospital.dto.DiagnoseDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DiagnoseRepository diagnoseRepository;

    private final String FIRST_NAME_PATIENT = "Waldemar";
    private final String LAST_NAME_PATIENT = "Walasiak";
    private final String USERNAME_PATIENT = "Walo";
    private final LocalDate BIRTHDAY = LocalDate.of(1990, 2, 11);
    private final StaffRole ROLE_PATIENT = new StaffRole("ROLE_PATIENT");

    private final String FIRST_NAME_DOCTOR = "Wojciech";
    private final String LAST_NAME_DOCTOR = "Puczyk";
    private final String USERNAME_DOCTOR = "Wojtas";
    private final String DOCTOR_SPECIALIZATION = "SURGEON";
    private final StaffRole ROLE_DOCTOR = new StaffRole("ROLE_DOCTOR");

    private final Patient PATIENT = new Patient(FIRST_NAME_PATIENT, LAST_NAME_PATIENT, USERNAME_PATIENT,
            BIRTHDAY, ROLE_PATIENT);
    private final HospitalStaff DOCTOR = new HospitalStaff(FIRST_NAME_DOCTOR, LAST_NAME_DOCTOR,
            USERNAME_DOCTOR, DOCTOR_SPECIALIZATION, ROLE_DOCTOR);

    private final String SORT_FIELD_DIAGNOSE = "diagnoseDate";
    private final String SORT_FIELD_APPOINTMENT = "date";
    private final String SORT_DIRECTION = "desc";
    private final int USER_ID = 1;
    private final Pageable DIAGNOSE_PAGEABLE = PageRequest.of(0, 5, Sort.by(SORT_FIELD_DIAGNOSE).descending());
    private final Pageable APPOINTMENT_PAGEABLE = PageRequest.of(0, 5,
            Sort.by(SORT_FIELD_APPOINTMENT).descending());

    @Test
    void getAllPatientAppointmentsPaginatedPositiveCase() {
        String APPOINTMENT_TYPE = "CONSULTATION";
        String APPOINTMENT_SUMMARY = "BRAIN STORM";
        LocalDateTime EVENT_DAY = LocalDateTime.of(2022, 8, 15, 16, 15);
        Appointment appointment = new Appointment(EVENT_DAY, APPOINTMENT_TYPE, APPOINTMENT_SUMMARY, PATIENT, DOCTOR);
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        Page<Appointment> page = new PageImpl<>(appointmentList, APPOINTMENT_PAGEABLE, 1L);


        Mockito.when(appointmentRepository.findAppointmentByPatientId(USER_ID, APPOINTMENT_PAGEABLE))
                .thenReturn(Optional.of(page));
        Page<AppointmentDto> appointmentPage = patientService.getAllPatientAppointmentsPaginated(1, 5,
                SORT_FIELD_APPOINTMENT, SORT_DIRECTION, USER_ID);
        AppointmentDto appointmentTest = appointmentPage.getContent().get(0);


        assertEquals(EVENT_DAY, appointmentTest.getDate());
        assertEquals(FIRST_NAME_PATIENT, appointmentTest.getPatient().getFirstname());
        assertEquals(USERNAME_DOCTOR, appointmentTest.getHospitalStaff().getUsername());
        assertEquals(DOCTOR_SPECIALIZATION, appointmentTest.getHospitalStaff().getDoctorSpecialization());
        assertEquals(APPOINTMENT_SUMMARY, appointmentTest.getSummary());
    }

    @Test
    void getAllPatientAppointmentsPaginatedNegativeCaseWithoutAnyAppointments() {
        List<Appointment> appointmentList = new ArrayList<>();
        Page<Appointment> page = new PageImpl<>(appointmentList, APPOINTMENT_PAGEABLE, 1L);

        Mockito.when(appointmentRepository.findAppointmentByPatientId(USER_ID, APPOINTMENT_PAGEABLE))
                .thenReturn(Optional.of(page));

        assertThrows(IncorrectDataException.class, () -> patientService
                .getAllPatientAppointmentsPaginated(1, 5, SORT_FIELD_APPOINTMENT, SORT_DIRECTION, USER_ID));
    }

    @Test
    void getAllPatientDiagnosesPaginated() {
        String diagnoseSummary = "headache";

        LocalDate diagnoseDate = LocalDate.of(2022, 8, 20);
        Diagnose diagnose = new Diagnose(diagnoseSummary, diagnoseDate, PATIENT, DOCTOR);
        List<Diagnose> diagnoseList = new ArrayList<>();
        diagnoseList.add(diagnose);
        Page<Diagnose> diagnosePage = new PageImpl<>(diagnoseList);

        Mockito.when(diagnoseRepository.findDiagnoseByPatientId(USER_ID, DIAGNOSE_PAGEABLE))
                .thenReturn(Optional.of(diagnosePage));
        Page<DiagnoseDto> diagnosePageTest = patientService
                .getAllPatientDiagnosesPaginated(1, 5, SORT_FIELD_DIAGNOSE, SORT_DIRECTION, USER_ID);
        DiagnoseDto diagnoseTest = diagnosePageTest.getContent().get(0);


        assertEquals(diagnoseDate, diagnoseTest.getDiagnoseDate());
        assertEquals(FIRST_NAME_PATIENT, diagnoseTest.getPatient().getFirstname());
        assertEquals(USERNAME_DOCTOR, diagnoseTest.getHospitalStaff().getUsername());
        assertEquals(DOCTOR_SPECIALIZATION, diagnoseTest.getHospitalStaff().getDoctorSpecialization());
        assertEquals(diagnoseSummary, diagnoseTest.getSummary());
    }

    @Test
    void getAllPatientDiagnosesPaginatedNegativeCaseWithoutAnyDiagnose() {
        List<Diagnose> diagnoseList = new ArrayList<>();
        Page<Diagnose> diagnosePage = new PageImpl<>(diagnoseList);

        Mockito.when(diagnoseRepository.findDiagnoseByPatientId(USER_ID, DIAGNOSE_PAGEABLE)).thenReturn(Optional.of(diagnosePage));

        assertThrows(IncorrectDataException.class, () -> patientService
                .getAllPatientDiagnosesPaginated(1, 5, SORT_FIELD_DIAGNOSE, SORT_DIRECTION, USER_ID));
    }

    @Test
    void getAllPatientDiagnosesPaginatedNegativeCaseListIsEmpty() {

        Mockito.when(diagnoseRepository.findDiagnoseByPatientId(USER_ID, DIAGNOSE_PAGEABLE))
                .thenReturn(Optional.of(Page.empty()));

        assertThrows(IncorrectDataException.class, () -> patientService
                .getAllPatientDiagnosesPaginated(1, 5, SORT_FIELD_DIAGNOSE, SORT_DIRECTION, USER_ID));
    }

    @Test
    void getAllPatientAppointmentsPaginatedNegativeCaseListIsEmpty() {

        Mockito.when(appointmentRepository.findAppointmentByPatientId(USER_ID, APPOINTMENT_PAGEABLE))
                .thenReturn(Optional.of(Page.empty()));

        assertThrows(IncorrectDataException.class, () -> patientService
                .getAllPatientAppointmentsPaginated(1, 5, SORT_FIELD_APPOINTMENT, SORT_DIRECTION, USER_ID));
    }

    @Test
    void getSortedPositiveCaseDesc() {
        String sortField = "firstname";
        String sortDirection = "DESC";

        Sort sort = patientService.getSort(sortField, sortDirection);

        for (var element : sort
        ) {
            assertEquals(sortField, element.getProperty());
            assertEquals(sortDirection, element.getDirection().name());
        }
    }

    @Test
    void getSortedPositiveCaseAsc() {
        String sortField = "lastname";
        String sortDirection = "ASC";

        Sort sort = patientService.getSort(sortField, sortDirection);

        for (var element : sort
        ) {
            assertEquals(sortField, element.getProperty());
            assertEquals(sortDirection, element.getDirection().name());
        }
    }
}