package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.*;
import com.khmal.hospital.dao.repository.*;
import com.khmal.hospital.dto.*;
import com.khmal.hospital.dto.request.HospitalStuffDtoUserDtoRoleDto;
import com.khmal.hospital.dto.request.PatientDtoUserDtoRoleDto;
import com.khmal.hospital.mapper.*;
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import com.khmal.hospital.service.validator.Validation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@Validated
public class RegistrationService {

    private final RoleRepository roleRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final HospitalStuffRepository hospitalStuffRepository;
    private final StuffRoleRepository stuffRoleRepository;
    private final Validation validation;
    private static final String ENCODE_ALGORITHM = "{bcrypt}";

    public RegistrationService(RoleRepository roleRepository, PatientRepository patientRepository,
                               UserRepository userRepository, HospitalStuffRepository hospitalStuffRepository, StuffRoleRepository stuffRoleRepository, Validation validation) {
        this.roleRepository = roleRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.hospitalStuffRepository = hospitalStuffRepository;
        this.stuffRoleRepository = stuffRoleRepository;
        this.validation = validation;
    }

    public PatientDto addNewPatient(@NotBlank(message = "Field firstname can't be empty") String firstname,
                                    @NotBlank(message = "Field lastname can't be empty") String lastname,
                                    @NotBlank(message = "Field username can't be empty") String username,
                                    @NotNull(message = "Field birthday can't be empty") LocalDate birthday,
                                    @NotNull(message = "Field stuffRoleId can't be empty") int stuffRoleId) {
        Patient patient = null;

        if (validation.checkStuffRoleInDataBase(stuffRoleId)) {

            patient = new Patient(firstname,
                    lastname,
                    username,
                    birthday,
                    stuffRoleRepository.getStuffRoleById(stuffRoleId).get());
        }

        patientRepository.save(patient);

        return PatientMapper.INSTANCE.toDto(patient);
    }

    public UserDto addNewUserToSecurityTable(@NotBlank(message = "Username can't be empty") String username,
                                             @NotBlank(message = "Password can't be empty") String password) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(password);

        User user = new User(username, ENCODE_ALGORITHM + encryptedPassword);
        userRepository.save(user);

        return UserMapper.INSTANCE.toDto(user);
    }

    public void addUserRoleToSecurityTable(@NotBlank(message = "Username can't be empty") String username,
                                           @NotNull(message = "Role id can't be empty") int roleId) {

        if (validation.checkStuffRoleInDataBase(roleId)) {
            Role role = new Role(
                    userRepository.getUserByUsername(username)
                            .orElseThrow(() ->
                                    new NoSuchUserException("User with username " + username + " is not found")),

                    stuffRoleRepository.getStuffRoleById(roleId)
                            .orElseThrow(() -> new IncorrectDateException("Role with id " + roleId + " is not found!"))
                            .getRoleName());

            roleRepository.save(role);
        }
    }

    public HospitalStuffDto addNewEmployee(@NotBlank(message = "Firstname can't be empty") String firstname,
                                           @NotBlank(message = "Lastname can't be empty") String lastname,
                                           @NotBlank(message = "Username can't be empty") String username,
                                           String doctorSpecialization,
                                           @NotNull int stuffRoleId) {

        HospitalStuff hospitalStuff = null;

        if (validation.checkStuffRoleInDataBase(stuffRoleId)) {
            hospitalStuff = new HospitalStuff(
                    firstname,
                    lastname,
                    username,
                    doctorSpecialization,
                    stuffRoleRepository.getStuffRoleById(stuffRoleId).orElseThrow(
                            () -> new IncorrectDateException("Role is not found in data base")
                    ));
        }
        return HospitalStuffMapper.INSTANCE.toDto(hospitalStuffRepository.save(hospitalStuff));
    }

    public List<PatientDto> getAllPatients() {

        if (patientRepository.findAll().isEmpty()) {
            throw new IncorrectDateException("No patients registered");
        }

        return PatientMapper.INSTANCE.toDto(patientRepository.findAll());
    }

    public List<HospitalStuffDto> getAllDoctors() {
        return HospitalStuffMapper.INSTANCE.toDto(
                hospitalStuffRepository.getHospitalStuffByDoctorSpecializationIsNotNull()
                        .orElseThrow(() -> new NoSuchUserException("No doctors registered")));
    }


    public Page<DoctorDto> getAllDoctorsPaginated1(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<HospitalStuff> hospitalStuffPage = hospitalStuffRepository.getHospitalStuffByDoctorSpecializationIsNotNull(pageable);

        Page<DoctorDto> doctorDtoPage = DoctorDtoPageMapper.toDto(hospitalStuffPage);

        return doctorDtoPage;
    }

    public Page<PatientDto> getAllPatientsPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Patient> patientPage = patientRepository.findAll(pageable);

        Page<PatientDto> patientDtoPage = PatientDtoPageMapper.toDto(patientPage);

        return patientDtoPage;
    }

    public void appointDoctorToPatient(@NotNull(message = "Doctor can't be empty") int doctorId,
                                       @NotNull(message = "Patient can't be empty") int patientId) {


        if (validation.checkPatientId(patientId) && validation.checkHospitalStuffId(doctorId)) {

            if (!validation.checkAppoint(doctorId, patientId))
                throw new IncorrectDateException("Appoint already exist");

            HospitalStuff hospitalStuff = hospitalStuffRepository.getHospitalStuffById(doctorId)
                    .orElseThrow(() -> new NoSuchUserException("Doctor is not found"));

            List<Patient> patientList = hospitalStuff.getPatientsList();

            Patient patient = patientRepository.getPatientById(patientId).orElseThrow(
                    () -> new NoSuchUserException("Patient is not found"));
            hospitalStuff.setPatientCount(hospitalStuff.getPatientCount() + 1);
            patient.setDischarged(false);

            patientList.add(patient);

            hospitalStuffRepository.save(hospitalStuff);
        }
    }

    public PatientDto getPatientById(int id) {
        Patient patient = patientRepository.getPatientById(id)
                .orElseThrow(() -> new NoSuchUserException("User is not found"));
        return PatientMapper.INSTANCE.toDto(patient);
    }

    public List<HospitalStuff.DoctorSpecialization> getAllDoctorSpecializations() {
        return Arrays.asList(HospitalStuff.DoctorSpecialization.values());
    }

    public List<StuffRoleDto> getAllStaffRoles() {
        return StuffRoleMapper.INSTANCE.toDto(stuffRoleRepository.findAll());
    }

    @Transactional
    public HospitalStuffDto addEmployeeToTheSystem(
            @NotNull(message = "Request to create employee can't be empty")
            HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto) {

        HospitalStuffDto employee = addNewEmployee(
                hospitalStuffDtoUserDtoRoleDto.getFirstname(),
                hospitalStuffDtoUserDtoRoleDto.getLastname(),
                hospitalStuffDtoUserDtoRoleDto.getUsername(),
                hospitalStuffDtoUserDtoRoleDto.getDoctorSpecialization(),
                hospitalStuffDtoUserDtoRoleDto.getStuffRoleId()
        );

        addNewUserToSecurityTable(
                hospitalStuffDtoUserDtoRoleDto.getUsername(),
                hospitalStuffDtoUserDtoRoleDto.getPassword());

        addUserRoleToSecurityTable(
                hospitalStuffDtoUserDtoRoleDto.getUsername(),
                hospitalStuffDtoUserDtoRoleDto.getStuffRoleId());

        return employee;
    }

    @Transactional
    public PatientDto addPatientToTheSystem(
            @NotNull(message = "Request to create employee can't be empty")
            PatientDtoUserDtoRoleDto patientDtoUserDtoRoleDto) {

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

        return patient;
    }
}
