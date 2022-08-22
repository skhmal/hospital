package com.khmal.hospital.service;

import com.khmal.hospital.controller.exception.handling.IncorrectDataException;
import com.khmal.hospital.controller.exception.handling.NoSuchUserException;
import com.khmal.hospital.dao.entity.*;
import com.khmal.hospital.dao.repository.*;
import com.khmal.hospital.dto.DoctorDto;
import com.khmal.hospital.dto.HospitalStaffDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.UserDto;
import com.khmal.hospital.dto.request.HospitalStaffDtoUserDtoRoleDto;
import com.khmal.hospital.dto.request.PatientDtoUserDtoRoleDto;
import com.khmal.hospital.mapper.*;
import com.khmal.hospital.service.validator.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class for registration new users(doctors, administrations, nurses, patients).
 */

@Service
@Validated
public class RegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);
    private final RoleRepository roleRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final HospitalStaffRepository hospitalStaffRepository;
    private final StaffRoleRepository staffRoleRepository;
    private final Validation validation;
    private static final String ENCODE_ALGORITHM = "{bcrypt}";

    public RegistrationService(RoleRepository roleRepository, PatientRepository patientRepository,
                               UserRepository userRepository, HospitalStaffRepository hospitalStaffRepository, StaffRoleRepository staffRoleRepository, Validation validation) {
        this.roleRepository = roleRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.hospitalStaffRepository = hospitalStaffRepository;
        this.staffRoleRepository = staffRoleRepository;
        this.validation = validation;
    }

    /**
     * Method for adding new patients to the system.
     *
     * @param firstname   first name
     * @param lastname    last name
     * @param username    username
     * @param birthday    date of birthday
     * @param staffRoleId roles id in database (checked before adding patient to the system).
     * @return PatientDto
     */
    public PatientDto addNewPatient(@Valid @NotBlank(message = "Field firstname can't be empty") String firstname,
                                    @Valid @NotBlank(message = "Field lastname can't be empty") String lastname,
                                    @Valid @NotBlank(message = "Field username can't be empty") String username,
                                    @Valid @NotBlank(message = "Field birthday can't be empty") LocalDate birthday,
                                    @Valid @NotNull(message = "Field staffRoleId can't be empty") Integer staffRoleId) {

        StaffRole staffRole = validation.checkStaffRoleInDataBase(staffRoleId);

        logger.info("Method addNewPatient started");

        Patient patient = new Patient(firstname,
                lastname,
                username,
                birthday,
                staffRole);

        patientRepository.save(patient);

        logger.info("Method addNewPatient finished. Patient with id {} has been created", patient.getId());

        return PatientMapper.INSTANCE.toDto(patient);
    }

    /**
     * Add new user to spring security table. Password encrypted before adding to database.
     *
     * @param username username
     * @param password password
     * @return UserDto
     */
    public UserDto addNewUserToSecurityTable(String username,
                                             String password) {

        logger.info("Method addNewUserToSecurityTable started. Username = {}", username);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(password);

        User user = new User(username, ENCODE_ALGORITHM + encryptedPassword);

        userRepository.save(user);

        logger.info("Method addNewUserToSecurityTable finished.");

        return UserMapper.INSTANCE.toDto(user);
    }

    /**
     * Add user to the authorities table(spring security).
     * - Check username in table users before adding to the system.
     * - Check role id in database before adding to the system.
     *
     * @param username username
     * @param roleId   role id
     */
    public void addUserRoleToSecurityTable(String username,
                                           int roleId) {

        logger.info("Method addUserRoleToSecurityTable started. Username = {}, roleId = {}", username, roleId);

        StaffRole staffRole = validation.checkStaffRoleInDataBase(roleId);

        Role role = new Role(
                userRepository.getUserByUsername(username)
                        .orElseThrow(() ->
                                new NoSuchUserException("User with username " + username + " is not found")),

                staffRole.getRoleName());

        roleRepository.save(role);

        logger.info("Method addUserRoleToSecurityTable finished. Username = {} with authorities id = {}", username,
                role.getId());
    }

    /**
     * Add new employee(administrator, doctor, nurse) to  the system.
     * - Checking role id in database before adding to the database
     *
     * @param firstname            first name
     * @param lastname             last name
     * @param username             username
     * @param doctorSpecialization doctor specialization(only for doctors). another have a null.
     * @param stuffRoleId          role id (for example administrators have 1, nurse 2, doctors 3, patients 4)
     * @return HospitalStaffDto
     */
    public HospitalStaffDto addNewEmployee(String firstname,
                                           String lastname,
                                           String username,
                                           String doctorSpecialization,
                                           int stuffRoleId) {


        logger.info("Method addNewEmployee started. Employee username = {}", username);

        StaffRole staffRole = validation.checkStaffRoleInDataBase(stuffRoleId);

        HospitalStaff hospitalStaff = new HospitalStaff(
                firstname,
                lastname,
                username,
                doctorSpecialization,
                staffRole);

        hospitalStaffRepository.save(hospitalStaff);

        logger.info("Method addNewEmployee finished. Employee with id = {} has been created", hospitalStaff.getId());

        return HospitalStuffMapper.INSTANCE.toDto(hospitalStaff);
    }

    /**
     * Get list of all patients in the system.
     *
     * @return list of patients
     */
    public List<PatientDto> getAllPatients() {

        logger.info("Method getAllPatients started");

        List<Patient> patientList = patientRepository.findAll();

        if (patientList.isEmpty()) {

            logger.warn("Method getAllPatients warn: No patients registered");

            throw new IncorrectDataException("No patients registered");
        }

        logger.info("Method getAllPatients finished");

        return PatientMapper.INSTANCE.toDto(patientList);
    }

    /**
     * Get list of all doctors in the system.
     *
     * @return list of doctors
     */
    public List<HospitalStaffDto> getAllDoctors() {
        logger.info("Method getAllDoctors");

        List<HospitalStaff> doctorList = hospitalStaffRepository.getHospitalStuffByDoctorSpecializationIsNotNull()
                .orElseThrow(() -> new NoSuchUserException("No doctors registered"));

        return HospitalStuffMapper.INSTANCE.toDto(doctorList);

    }

    /**
     * Get paginated list of all doctors in the system.
     *
     * @return paginated list of doctors
     */
    public Page<DoctorDto> getAllDoctorsPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        logger.info("Method getAllDoctorsPaginated started");
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<HospitalStaff> hospitalStuffPage = hospitalStaffRepository.getHospitalStaffByDoctorSpecializationIsNotNull(pageable);

        Page<DoctorDto> doctorDtoPage = DoctorPaginationMapper.toDto(hospitalStuffPage);

        logger.info("Method getAllDoctorsPaginated finished");
        return doctorDtoPage;
    }

    /**
     * Get paginated list of all patients in the system.
     *
     * @return paginated list of patients
     */
    public Page<PatientDto> getAllPatientsPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        logger.info("Method getAllPatientsPaginated started");

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Patient> patientPage = patientRepository.findAll(pageable);

        Page<PatientDto> patientDtoPage = PatientPaginationMapper.toDto(patientPage);

        logger.info("Method getAllPatientsPaginated finished");

        return patientDtoPage;
    }

    /**
     * Appoint doctor to patient.
     * - Checking patient and doctor id's in the database.
     * - Checking for double appoint.
     *
     * @param doctorId  doctor id
     * @param patientId patient id
     */
    public void appointDoctorToPatient(@Valid @Min(value = 1, message = "Field doctor can't be empty") Integer doctorId,
                                       @Valid @Min(value = 1, message = "Patient can't be empty") Integer patientId) {

        logger.info("Method appointDoctorToPatient started. Doctor id = {}, patient id = {}.", doctorId, patientId);

        HospitalStaff doctor = validation.checkHospitalStaffId(doctorId);
        Patient patient = validation.checkPatientId(patientId);


        if (!validation.checkDoubleAppoint(doctorId, patientId))
            throw new IncorrectDataException("Appoint already exist");

        List<Patient> patientList = doctor.getPatientsList();

        doctor.setPatientCount(doctor.getPatientCount() + 1);
        patient.setDischarged(false);

        patientList.add(patient);

        hospitalStaffRepository.save(doctor);

        logger.info("Method appointDoctorToPatient finished. Appoint with id = {} has been created.", doctor.getId());
    }

    /**
     * Transactional method which include three methods (addNewEmployee, addNewUserToSecurityTable,
     * addUserRoleToSecurityTable).
     * - Checking doctor specialization to prevent provide incorrect doctor specialization to the system.
     *
     * @param hospitalStaffDtoUserDtoRoleDto request from view.
     * @return HospitalStaffDto
     */
    @Transactional
    public HospitalStaffDto addEmployeeToTheSystem(
            @Valid HospitalStaffDtoUserDtoRoleDto hospitalStaffDtoUserDtoRoleDto) {

        logger.info("Method addEmployeeToTheSystem started. Username = {}.", hospitalStaffDtoUserDtoRoleDto.getUsername());

        validation.checkDoctorSpecialization(hospitalStaffDtoUserDtoRoleDto.getDoctorSpecialization());

        addNewUserToSecurityTable(
                hospitalStaffDtoUserDtoRoleDto.getUsername(),
                hospitalStaffDtoUserDtoRoleDto.getPassword());

        addUserRoleToSecurityTable(
                hospitalStaffDtoUserDtoRoleDto.getUsername(),
                hospitalStaffDtoUserDtoRoleDto.getStuffRoleId());

        HospitalStaffDto employee = addNewEmployee(
                hospitalStaffDtoUserDtoRoleDto.getFirstname(),
                hospitalStaffDtoUserDtoRoleDto.getLastname(),
                hospitalStaffDtoUserDtoRoleDto.getUsername(),
                hospitalStaffDtoUserDtoRoleDto.getDoctorSpecialization(),
                hospitalStaffDtoUserDtoRoleDto.getStuffRoleId()
        );

        logger.info("Method addEmployeeToTheSystem finished");

        return employee;
    }

    /**
     * Transactional method which include three methods (addNewPatient, addNewUserToSecurityTable,
     * addUserRoleToSecurityTable).
     *
     * @param patientDtoUserDtoRoleDto request from view.
     * @return PatientDto
     */
    @Transactional
    public PatientDto addPatientToTheSystem(
            @Valid PatientDtoUserDtoRoleDto patientDtoUserDtoRoleDto) {

        logger.info("Method addPatientToTheSystem started. Username = {}.", patientDtoUserDtoRoleDto.getUsername());

        addNewUserToSecurityTable(
                patientDtoUserDtoRoleDto.getUsername(),
                patientDtoUserDtoRoleDto.getPassword());

        addUserRoleToSecurityTable(
                patientDtoUserDtoRoleDto.getUsername(),
                patientDtoUserDtoRoleDto.getRoleId());

        PatientDto patient = addNewPatient(
                patientDtoUserDtoRoleDto.getFirstName(),
                patientDtoUserDtoRoleDto.getLastname(),
                patientDtoUserDtoRoleDto.getUsername(),
                patientDtoUserDtoRoleDto.getBirthday(),
                patientDtoUserDtoRoleDto.getRoleId()

        );

        logger.info("Method addPatientToTheSystem finished");

        return patient;
    }

    public int getRoleIdByName(String name) {
        logger.info("Method getRoleIdByName(name = {}) started.", name);

        StaffRole role = staffRoleRepository.findStaffRoleByRoleName(name).orElseThrow(
                () -> new IncorrectDataException("Role with name " + name + " is not found"));

        logger.info("Method getRoleIdByName(name = {}) finished.", name);
        return role.getId();
    }
}
