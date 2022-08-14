package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.*;
import com.khmal.hospital.dao.repository.*;
import com.khmal.hospital.dto.DoctorDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.request.HospitalStaffDtoUserDtoRoleDto;
import com.khmal.hospital.dto.request.PatientDtoUserDtoRoleDto;
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import com.khmal.hospital.service.validator.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private HospitalStaffRepository hospitalStaffRepository;
    @Mock
    private StaffRoleRepository staffRoleRepository;
    @Mock
    private Validation validation;


    private final String PASSWORD = "{noop}1234";
    private final String PATIENT_FIRSTNAME = "Jan";
    private final String PATIENT_LASTNAME = "Grabowski";
    private final String PATIENT_USERNAME = "jb";
    private final LocalDate PATIENT_BIRTHDAY = LocalDate.of(1991, 2, 28);
    private final String ROLE_PATIENT = "ROLE_PATIENT";
    private final StaffRole STAFF_ROLE_PATIENT = new StaffRole(ROLE_PATIENT);
    private final Patient PATIENT = new Patient(PATIENT_FIRSTNAME, PATIENT_LASTNAME,
            PATIENT_USERNAME, PATIENT_BIRTHDAY, STAFF_ROLE_PATIENT);


    private final int DOCTOR_ID = 1;
    private final String DOCTOR_FIRSTNAME = "john";
    private final String DOCTOR_LASTNAME = "schwarc";
    private final String DOCTOR_USERNAME = "js";
    private final String DOCTOR_SPECIALIZATION = "surgeon";
    private final String ROLE_DOCTOR = "ROLE_DOCTOR";
    private final StaffRole STAFF_ROLE_DOCTOR = new StaffRole(ROLE_DOCTOR);
    private final HospitalStaff DOCTOR = new HospitalStaff(DOCTOR_ID, DOCTOR_FIRSTNAME, DOCTOR_LASTNAME,
            DOCTOR_USERNAME, DOCTOR_SPECIALIZATION, STAFF_ROLE_DOCTOR);

    private final String sortField = "firstname";
    private final String sortDirection = "desc";
    private final Pageable pageable = PageRequest.of(0, 5, Sort.by(sortField).descending());

    @BeforeEach
    public void setUp() {
        Mockito.when(staffRoleRepository.getStuffRoleById(Mockito.anyInt())).thenReturn(Optional.of(STAFF_ROLE_PATIENT));

        DOCTOR.setPatientsList(new ArrayList<Patient>());

        Mockito.when(hospitalStaffRepository.getHospitalStuffById(Mockito.anyInt())).thenReturn(Optional.of(DOCTOR));
        Mockito.when(patientRepository.getPatientById(Mockito.anyInt())).thenReturn(Optional.of(PATIENT));

        Mockito.when(validation.checkHospitalStuffId(Mockito.anyInt())).thenReturn(true);
        Mockito.when(validation.checkPatientId(Mockito.anyInt())).thenReturn(true);
        Mockito.when(validation.checkStuffRoleInDataBase(Mockito.anyInt())).thenReturn(true);
        Mockito.when(validation.checkDoubleAppoint(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
    }

    @Test
    void addNewPatientPositiveCase() {
        int stuffRoleId = 4;

        registrationService.addNewPatient(PATIENT_FIRSTNAME, PATIENT_LASTNAME, PATIENT_USERNAME,
                PATIENT_BIRTHDAY, stuffRoleId);

        ArgumentCaptor<Patient> patientArgumentCaptor = ArgumentCaptor.forClass(Patient.class);
        Mockito.verify(patientRepository).save(patientArgumentCaptor.capture());
        Patient patient = patientArgumentCaptor.getValue();

        assertEquals(PATIENT_USERNAME, patient.getUsername());
        assertEquals(PATIENT_FIRSTNAME, patient.getFirstname());
        assertEquals(ROLE_PATIENT, patient.getStaffRole().getRoleName());
        assertEquals(PATIENT_BIRTHDAY, patient.getBirthday());
    }

    @Test
    void addNewUserToSecurityTablePositiveCase() {
        int expectedEnabled = 1;

        registrationService.addNewUserToSecurityTable(PATIENT_USERNAME, PASSWORD);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();

        assertEquals(PATIENT_USERNAME, user.getUsername());
        assertEquals(expectedEnabled, user.getEnabled());
    }

    @Test
    void addNewUserRoleToSecurityTablePositiveCase() {
        int roleId = 4;

        Mockito.when(userRepository.getUserByUsername(PATIENT_USERNAME))
                .thenReturn(Optional.of(new User(PATIENT_USERNAME, PASSWORD)));
        registrationService.addUserRoleToSecurityTable(PATIENT_USERNAME, roleId);

        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
        Mockito.verify(roleRepository).save(roleArgumentCaptor.capture());
        Role role = roleArgumentCaptor.getValue();

        assertEquals(ROLE_PATIENT, role.getRoleName());
    }

    @Test
    void addNewEmployeePositiveCase() {

        Mockito.when(staffRoleRepository.getStuffRoleById(Mockito.anyInt())).thenReturn(Optional.of(STAFF_ROLE_DOCTOR));
        registrationService.addNewEmployee(DOCTOR_FIRSTNAME, DOCTOR_LASTNAME, DOCTOR_USERNAME, DOCTOR_SPECIALIZATION, Mockito.anyInt());

        ArgumentCaptor<HospitalStaff> hospitalStuffDtoArgumentCaptor = ArgumentCaptor.forClass(HospitalStaff.class);
        Mockito.verify(hospitalStaffRepository).save(hospitalStuffDtoArgumentCaptor.capture());
        HospitalStaff hospitalStaff = hospitalStuffDtoArgumentCaptor.getValue();

        assertEquals(DOCTOR_SPECIALIZATION, hospitalStaff.getDoctorSpecialization());
    }

    @Test
    void appointDoctorToPatientPositiveCase() {
        registrationService.appointDoctorToPatient(Mockito.anyInt(), 1);

        ArgumentCaptor<HospitalStaff> hospitalStuffArgumentCaptor = ArgumentCaptor.forClass(HospitalStaff.class);
        Mockito.verify(hospitalStaffRepository).save(hospitalStuffArgumentCaptor.capture());
        HospitalStaff hospitalStuff = hospitalStuffArgumentCaptor.getValue();

        assertEquals(PATIENT_FIRSTNAME, hospitalStuff.getPatientsList().get(0).getFirstname());
    }

    @Test
    void getAllPatientsPositiveCase() {
        List<Patient> patientList = new ArrayList<>();
        Patient firstPatient = new Patient();
        Patient secondPatient = new Patient();
        patientList.add(firstPatient);
        patientList.add(secondPatient);

        Mockito.when(patientRepository.findAll()).thenReturn(patientList);

        assertEquals(patientList.size(), registrationService.getAllPatients().size());
    }

    @Test
    void getAllPatientsNegativeCase() {
        Mockito.when(patientRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(IncorrectDateException.class, () -> registrationService.getAllPatients());
    }

    @Test
    void getAllDoctorsPositiveCase() {
        List<HospitalStaff> doctorList = new ArrayList<>();
        HospitalStaff firstDoctor = new HospitalStaff();
        doctorList.add(firstDoctor);

        Mockito.when(hospitalStaffRepository.getHospitalStuffByDoctorSpecializationIsNotNull())
                .thenReturn(Optional.of(doctorList));

        assertEquals(doctorList.size(), registrationService.getAllDoctors().size());
    }

    @Test
    void getAllDoctorsNegativeCase() {
        Mockito.when(hospitalStaffRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(NoSuchUserException.class, () -> registrationService.getAllDoctors());
    }

    @Test
    void getAllPatientsPaginatedPositiveCase() {
        List<Patient> patientList = new ArrayList<>();
        patientList.add(PATIENT);
        Page<Patient> patientPage = new PageImpl<>(patientList, pageable, 1);

        Mockito.when(patientRepository.findAll(pageable)).thenReturn(patientPage);
        Page<PatientDto> patientDtoPage = registrationService
                .getAllPatientsPaginated(1, 5, sortField, sortDirection);
        PatientDto patientDto = patientDtoPage.getContent().get(0);

        assertEquals(PATIENT_FIRSTNAME, patientDto.getFirstname());
    }

    @Test
    void getAllDoctorsPaginatedPositiveCase() {
        List<HospitalStaff> doctorList = new ArrayList<>();
        doctorList.add(DOCTOR);
        Page<HospitalStaff> doctorPage = new PageImpl<>(doctorList, pageable, 1L);

        Mockito.when(hospitalStaffRepository.getHospitalStaffByDoctorSpecializationIsNotNull(pageable)).thenReturn(doctorPage);
        Page<DoctorDto> doctorsPaginated = registrationService
                .getAllDoctorsPaginated(1, 5, sortField, sortDirection);
        DoctorDto doctorDto = doctorsPaginated.getContent().get(0);

        assertEquals(DOCTOR_FIRSTNAME, doctorDto.getFirstname());
    }

    @Test
    void addEmployeeToTheSystem(){
        HospitalStaffDtoUserDtoRoleDto hospitalStaffDtoUserDtoRoleDto = new HospitalStaffDtoUserDtoRoleDto();
        hospitalStaffDtoUserDtoRoleDto.setFirstname(DOCTOR_FIRSTNAME);
        hospitalStaffDtoUserDtoRoleDto.setLastname(DOCTOR_LASTNAME);
        hospitalStaffDtoUserDtoRoleDto.setUsername(DOCTOR_USERNAME);
        hospitalStaffDtoUserDtoRoleDto.setPassword(PASSWORD);
        hospitalStaffDtoUserDtoRoleDto.setDoctorSpecialization(DOCTOR_SPECIALIZATION);
        hospitalStaffDtoUserDtoRoleDto.setStuffRoleId(3);

        Mockito.when(staffRoleRepository.getStuffRoleById(Mockito.anyInt()))
                .thenReturn(Optional.of(STAFF_ROLE_DOCTOR));
        Mockito.when(userRepository.getUserByUsername(DOCTOR_USERNAME))
                .thenReturn(Optional.of(new User(DOCTOR_USERNAME, Mockito.anyString())));

        registrationService.addEmployeeToTheSystem(hospitalStaffDtoUserDtoRoleDto);

        ArgumentCaptor<HospitalStaff> doctorArgumentCaptor = ArgumentCaptor.forClass(HospitalStaff.class);
        Mockito.verify(hospitalStaffRepository).save(doctorArgumentCaptor.capture());
        HospitalStaff doctor = doctorArgumentCaptor.getValue();

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();

        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
        Mockito.verify(roleRepository).save(roleArgumentCaptor.capture());
        Role role = roleArgumentCaptor.getValue();

        assertEquals(DOCTOR_FIRSTNAME, doctor.getFirstname());
        assertEquals(DOCTOR_USERNAME,  user.getUsername());
        assertEquals(ROLE_DOCTOR, role.getRoleName());
    }

    @Test
    void addPatientToTheSystem(){
        PatientDtoUserDtoRoleDto patientDtoUserDtoRoleDto = new PatientDtoUserDtoRoleDto();
        patientDtoUserDtoRoleDto.setFirstName(PATIENT_FIRSTNAME);
        patientDtoUserDtoRoleDto.setLastname(PATIENT_LASTNAME);
        patientDtoUserDtoRoleDto.setUsername(PATIENT_USERNAME);
        patientDtoUserDtoRoleDto.setPassword(PASSWORD);
        patientDtoUserDtoRoleDto.setBirthday(PATIENT_BIRTHDAY);

        Mockito.when(staffRoleRepository.getStuffRoleById(Mockito.anyInt()))
                .thenReturn(Optional.of(STAFF_ROLE_PATIENT));
        Mockito.when(userRepository.getUserByUsername(PATIENT_USERNAME))
                .thenReturn(Optional.of(new User(PATIENT_USERNAME, Mockito.anyString())));

        registrationService.addPatientToTheSystem(patientDtoUserDtoRoleDto);

        ArgumentCaptor<Patient> patientArgumentCaptor = ArgumentCaptor.forClass(Patient.class);
        Mockito.verify(patientRepository).save(patientArgumentCaptor.capture());
        Patient patient = patientArgumentCaptor.getValue();

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();

        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
        Mockito.verify(roleRepository).save(roleArgumentCaptor.capture());
        Role role = roleArgumentCaptor.getValue();

        assertEquals(PATIENT_FIRSTNAME, patient.getFirstname());
        assertEquals(PATIENT_USERNAME,  user.getUsername());
        assertEquals(ROLE_PATIENT, role.getRoleName());
    }
}