package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.*;
import com.khmal.hospital.dao.repository.*;
import com.khmal.hospital.dto.HospitalStuffDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.StuffRoleDto;
import com.khmal.hospital.dto.UserDto;
import com.khmal.hospital.mapper.HospitalStuffMapper;
import com.khmal.hospital.mapper.PatientMapper;
import com.khmal.hospital.mapper.StuffRoleMapper;
import com.khmal.hospital.mapper.UserMapper;
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import com.khmal.hospital.service.validator.Validation;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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

        User user = new User(username, password);
        userRepository.save(user);

        return UserMapper.INSTANCE.toDto(user);
    }

    public void addUserRoleToSecurityTable(@NotBlank(message = "Username can't be empty")String username,
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

    public HospitalStuffDto addNewEmployee(@NotBlank(message = "Firstname can't be empty")String firstname,
                                           @NotBlank(message = "Lastname can't be empty")String lastname,
                                           @NotBlank(message = "Username can't be empty")String username,
                                           String doctorSpecialization,
                                           int stuffRoleId) {
        HospitalStuff hospitalStuff = null;

        if (doctorSpecialization.equals("null")){
            doctorSpecialization = null;
        }

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
                        .orElseThrow(() -> new NoSuchUserException("No registered doctors")));
    }

    public void appointDoctorToPatient(@NotNull(message = "Doctor can't be empty") int doctorId,
                                       @NotNull(message = "Patient can't be empty") int patientId) {

        if (validation.checkPatientId(patientId) && validation.checkHospitalStuffId(doctorId)) {

            HospitalStuff hospitalStuff = hospitalStuffRepository.getHospitalStuffById(doctorId)
                    .orElseThrow(() -> new NoSuchUserException("Doctor is not found"));

            List<Patient> patientList = hospitalStuff.getPatientsList();

            patientList.add(patientRepository.getPatientById(patientId).orElseThrow(
                    () -> new NoSuchUserException("Patient is not found")
            ));

            hospitalStuffRepository.save(hospitalStuff);
        }
    }

    public PatientDto getPatientById(int id){
       Patient patient = patientRepository.getPatientById(id).get();
//               .orElseThrow(() -> new NoSuchUserException("User is not found"));
       return PatientMapper.INSTANCE.toDto(patient);
    }

    public List<HospitalStuff.DoctorSpecialization> getAllDoctorSpecializations(){
        return Arrays.asList(HospitalStuff.DoctorSpecialization.values());
    }

    public List<StuffRoleDto> getAllStaffRoles(){
        return StuffRoleMapper.INSTANCE.toDto(stuffRoleRepository.findAll());
    }
}
