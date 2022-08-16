package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.entity.Role;
import com.khmal.hospital.dao.entity.User;
import com.khmal.hospital.dao.repository.*;
import com.khmal.hospital.dto.DoctorDto;
import com.khmal.hospital.dto.HospitalStaffDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.UserDto;
import com.khmal.hospital.dto.request.HospitalStaffDtoUserDtoRoleDto;
import com.khmal.hospital.dto.request.PatientDtoUserDtoRoleDto;
import com.khmal.hospital.mapper.*;
import com.khmal.hospital.service.exception_handling.IncorrectDataException;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
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
     * @param firstname first name
     * @param lastname last name
     * @param username username
     * @param birthday date of birthday
     * @param stuffRoleId roles id in database (checked before adding patient to the system).
     * @return PatientDto
     */
    public PatientDto addNewPatient(@NotBlank(message = "Field firstname can't be empty") String firstname,
                                    @NotBlank(message = "Field lastname can't be empty") String lastname,
                                    @NotBlank(message = "Field username can't be empty") String username,
                                    @NotBlank(message = "Field birthday can't be empty") LocalDate birthday,
                                    @NotNull(message = "Field stuffRoleId can't be empty") int stuffRoleId) {
        Patient patient = null;
        logger.info("Method addNewPatient started");

        if (validation.checkStaffRoleInDataBase(stuffRoleId)) {

            patient = new Patient(firstname,
                    lastname,
                    username,
                    birthday,
                    staffRoleRepository.getStuffRoleById(stuffRoleId).get());
        }

        patientRepository.save(patient);

        logger.info("Method addNewPatient finished");
        return PatientMapper.INSTANCE.toDto(patient);
    }

    /**
     * Add new user to spring security table. Password encrypted before adding to database.
     * @param username username
     * @param password password
     * @return UserDto
     */
    public UserDto addNewUserToSecurityTable(@NotBlank(message = "Username can't be empty") String username,
                                             @NotBlank(message = "Password can't be empty") String password) {

        logger.info("Method addNewuserToSecurityTable started");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(password);

        User user = new User(username, ENCODE_ALGORITHM + encryptedPassword);
        userRepository.save(user);

        logger.info("Method addNewuserToSecurityTable finished");

        return UserMapper.INSTANCE.toDto(user);
    }

    /**
     * Add user to the authorities table(spring security).
     * - Check username in table users before adding to the system.
     * - Check role id in database before adding to the system.
     * @param username username
     * @param roleId role id
     */
    public void addUserRoleToSecurityTable(@NotBlank(message = "Username can't be empty") String username,
                                           @NotNull(message = "Role id can't be empty") int roleId) {

        logger.info("Method addUserRoleToSecurityTable started");

        if (validation.checkStaffRoleInDataBase(roleId)) {
            Role role = new Role(
                    userRepository.getUserByUsername(username)
                            .orElseThrow(() ->
                                    new NoSuchUserException("User with username " + username + " is not found")),

                    staffRoleRepository.getStuffRoleById(roleId)
                            .orElseThrow(() -> new IncorrectDataException("Role with id " + roleId + " is not found!"))
                            .getRoleName());

            roleRepository.save(role);
            logger.info("Method addUserRoleToSecurityTable finished");
        }
    }

    /**
     * Add new employee(administrator, doctor, nurse) to  the system.
     * - Checking role id in database before adding to the database
     * @param firstname first name
     * @param lastname last name
     * @param username username
     * @param doctorSpecialization doctor specialization(only for doctors). another have a null.
     * @param stuffRoleId role id (for example administrators have 1, nurse 2, doctors 3, patients 4)
     * @return HospitalStaffDto
     */
    public HospitalStaffDto addNewEmployee(@NotBlank(message = "Firstname can't be empty") String firstname,
                                           @NotBlank(message = "Lastname can't be empty") String lastname,
                                           @NotBlank(message = "Username can't be empty") String username,
                                           String doctorSpecialization,
                                           @NotNull(message = "Role id can't be null") int stuffRoleId) {

        HospitalStaff hospitalStaff = null;

        logger.info("Method addNewEmployee started");
        if (validation.checkStaffRoleInDataBase(stuffRoleId)) {
            hospitalStaff = new HospitalStaff(
                    firstname,
                    lastname,
                    username,
                    doctorSpecialization,
                    staffRoleRepository.getStuffRoleById(stuffRoleId).orElseThrow(
                            () -> new IncorrectDataException("Role is not found in data base")
                    ));
        }
        logger.info("Method addNewEmployee finished");
        return HospitalStuffMapper.INSTANCE.toDto(hospitalStaffRepository.save(hospitalStaff));
    }

    /**
     * Get list of all patients in the system.
     * @return list of patients
     */
    public List<PatientDto> getAllPatients() {

        logger.info("Method getAllPatients started");
        if (patientRepository.findAll().isEmpty()) {
            logger.warn("Method getAllPatients warn: No patients registered");
            throw new IncorrectDataException("No patients registered");
        }

        logger.info("Method getAllPatients finished");
        return PatientMapper.INSTANCE.toDto(patientRepository.findAll());
    }

    /**
     * Get list of all doctors in the system.
     * @return list of doctors
     */
    public List<HospitalStaffDto> getAllDoctors() {
        logger.info("Method getAllDoctors");
        return HospitalStuffMapper.INSTANCE.toDto(
                hospitalStaffRepository.getHospitalStuffByDoctorSpecializationIsNotNull()
                        .orElseThrow(() -> new NoSuchUserException("No doctors registered")));
    }

    /**
     * Get paginated list of all doctors in the system.
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
     * @param doctorId doctor id
     * @param patientId patient id
     */
    public void appointDoctorToPatient(@NotNull(message = "Doctor can't be empty") int doctorId,
                                       @NotNull(message = "Patient can't be empty") int patientId) {

        logger.info("Method appointDoctorToPatient started");

        if (validation.checkPatientId(patientId) && validation.checkHospitalStaffId(doctorId)) {

            if (!validation.checkDoubleAppoint(doctorId, patientId))
                throw new IncorrectDataException("Appoint already exist");

            HospitalStaff doctor = hospitalStaffRepository.getHospitalStuffById(doctorId)
                    .orElseThrow(() -> new NoSuchUserException("Doctor is not found"));

            List<Patient> patientList = doctor.getPatientsList();

            Patient patient = patientRepository.getPatientById(patientId).orElseThrow(
                    () -> new NoSuchUserException("Patient is not found"));

            doctor.setPatientCount(doctor.getPatientCount() + 1);
            patient.setDischarged(false);

            patientList.add(patient);

            hospitalStaffRepository.save(doctor);

            logger.info("Method appointDoctorToPatient finished");
        }
    }

    /**
     * Transactional method which include three methods (addNewEmployee, addNewUserToSecurityTable,
     * addUserRoleToSecurityTable).
     * - Checking doctor specialization to prevent provide incorrect doctor specialization to the system.
     * @param hospitalStaffDtoUserDtoRoleDto request from view.
     * @return HospitalStaffDto
     */
    @Transactional
    public HospitalStaffDto addEmployeeToTheSystem(
            @NotNull(message = "Request to create employee can't be empty")
            @Valid HospitalStaffDtoUserDtoRoleDto hospitalStaffDtoUserDtoRoleDto) {

        logger.info("Method addEmployeeToTheSystem started");

        validation.checkDoctorSpecialization(hospitalStaffDtoUserDtoRoleDto.getDoctorSpecialization());

        HospitalStaffDto employee = addNewEmployee(
                hospitalStaffDtoUserDtoRoleDto.getFirstname(),
                hospitalStaffDtoUserDtoRoleDto.getLastname(),
                hospitalStaffDtoUserDtoRoleDto.getUsername(),
                hospitalStaffDtoUserDtoRoleDto.getDoctorSpecialization(),
                hospitalStaffDtoUserDtoRoleDto.getStuffRoleId()
        );

        addNewUserToSecurityTable(
                hospitalStaffDtoUserDtoRoleDto.getUsername(),
                hospitalStaffDtoUserDtoRoleDto.getPassword());

        addUserRoleToSecurityTable(
                hospitalStaffDtoUserDtoRoleDto.getUsername(),
                hospitalStaffDtoUserDtoRoleDto.getStuffRoleId());

        logger.info("Method addEmployeeToTheSystem finished");

        return employee;
    }

    /**
     * Transactional method which include three methods (addNewPatient, addNewUserToSecurityTable,
     *       addUserRoleToSecurityTable).
     * @param patientDtoUserDtoRoleDto request from view.
     * @return PatientDto
     */
    @Transactional
    public PatientDto addPatientToTheSystem(
            @NotNull(message = "Request to create employee can't be empty")
            PatientDtoUserDtoRoleDto patientDtoUserDtoRoleDto) {

        logger.info("Method addPatientToTheSystem started");

        if (patientDtoUserDtoRoleDto.getPassword().isBlank()) {
            logger.warn("Method addPatientToTheSystem warn. Password can't be empty");
            throw new IncorrectDataException("Password can't be empty");
        }

        int patientRoleId = 4;

        PatientDto patient = addNewPatient(
                patientDtoUserDtoRoleDto.getFirstName(),
                patientDtoUserDtoRoleDto.getLastname(),
                patientDtoUserDtoRoleDto.getUsername(),
                patientDtoUserDtoRoleDto.getBirthday(),
                patientRoleId

        );

        addNewUserToSecurityTable(
                patientDtoUserDtoRoleDto.getUsername(),
                patientDtoUserDtoRoleDto.getPassword());

        addUserRoleToSecurityTable(
                patientDtoUserDtoRoleDto.getUsername(),
                patientRoleId);

        logger.info("Method addPatientToTheSystem finished");

        return patient;
    }
}
