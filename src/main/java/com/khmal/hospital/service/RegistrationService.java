package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.HospitalStuff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.entity.Role;
import com.khmal.hospital.dao.entity.User;
import com.khmal.hospital.dao.repository.HospitalStuffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dao.repository.RoleRepository;
import com.khmal.hospital.dao.repository.UserRepository;
import com.khmal.hospital.dto.HospitalStuffDto;
import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.RoleDto;
import com.khmal.hospital.dto.UserDto;
import com.khmal.hospital.dto.mapper.HospitalStuffMapper;
import com.khmal.hospital.dto.mapper.PatientMapper;
import com.khmal.hospital.dto.mapper.RoleMapper;
import com.khmal.hospital.dto.mapper.UserMapper;
import com.khmal.hospital.service.exception_handling.IncorrectDateException;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
public class RegistrationService {

    private final RoleRepository roleRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final HospitalStuffRepository hospitalStuffRepository;

    public RegistrationService(RoleRepository roleRepository, PatientRepository patientRepository,
                               UserRepository userRepository, HospitalStuffRepository hospitalStuffRepository) {
        this.roleRepository = roleRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.hospitalStuffRepository = hospitalStuffRepository;
    }

    public void addNewPatient(@Valid PatientDto patientDto) {
            Patient patient = PatientMapper.INSTANCE.toEntity(patientDto);
            patientRepository.save(patient);
    }

    public void addNewUserToSecurityTable(@Valid UserDto userDto) {
            User user = UserMapper.INSTANCE.toEntity(userDto);
            userRepository.save(user);
    }

    public void addUserRoleToSecurityTable(@Valid RoleDto roleDto) {
            Role role = RoleMapper.INSTANCE.toEntity(roleDto);
            roleRepository.save(role);
    }

    public void addNewEmployee(@Valid HospitalStuffDto hospitalStuffDto) {
            HospitalStuff hospitalStuff = HospitalStuffMapper.INSTANCE.toEntity(hospitalStuffDto);
            hospitalStuffRepository.save(hospitalStuff);
    }

    public List<PatientDto> getAllPatients(){

        if(patientRepository.findAll().isEmpty()){
            throw new IncorrectDateException("No registered patients");
        }

        return PatientMapper.INSTANCE.toDto(patientRepository.findAll());
    }

    public List<HospitalStuffDto> getAllDoctors() {
        return HospitalStuffMapper.INSTANCE.toDto(
                hospitalStuffRepository.getHospitalStuffByDoctorSpecializationIsNotNull()
                        .orElseThrow(() -> new NoSuchUserException("No registered doctors")));
    }

    public void appointDoctorToPatient(@NotNull(message = "Doctor can't be empty") int doctorId,
                                       @NotNull(message = "Patient can't be empty") int patientId){

        HospitalStuff hospitalStuff = hospitalStuffRepository.getHospitalStuffById(doctorId)
                .orElseThrow(() -> new NoSuchUserException("Doctor is not found"));

        List<Patient> patientList = hospitalStuff.getPatientsList();

        patientList.add(patientRepository.getPatientById(patientId).orElseThrow(
                () -> new NoSuchUserException("Patient is not found")
        ));

        hospitalStuffRepository.save(hospitalStuff);
    }
}
