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
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public void addNewPatient(PatientDto patientDto) {


        try {
            Patient patient = PatientMapper.INSTANCE.toEntity(patientDto);

            patientRepository.save(patient);
        } catch (Exception e) {
            throw new NoSuchUserException("Patient is not saved");
        }
    }

    public void addNewUserToSecurityTable(UserDto userDto) {

        try {
            User user = UserMapper.INSTANCE.toEntity(userDto);

            userRepository.save(user);
        } catch (Exception e) {
            throw new NoSuchUserException("User is not saved");
        }

    }

    public void addUserRoleToSecurityTable(RoleDto roleDto) {

        try {
            Role role = RoleMapper.INSTANCE.toEntity(roleDto);

            roleRepository.save(role);
        } catch (Exception e) {
            throw new NoSuchUserException("Role is not saved");
        }

    }

    public void addNewEmployee(HospitalStuffDto hospitalStuffDto) {
        try {
            HospitalStuff hospitalStuff =
                    HospitalStuffMapper.INSTANCE.toEntity(hospitalStuffDto);

            hospitalStuffRepository.save(hospitalStuff);
        } catch (Exception e) {
            throw new NoSuchUserException("Employee is not saved");
        }
    }

    public List<PatientDto> getAllPatients(){
        List<Patient> patientList;
        try {
            patientList = patientRepository.findAll();
        }catch (Exception e){
            throw new NoSuchUserException("No one patients here");
        }
        return PatientMapper.INSTANCE.toDto(patientList);
    }

    public List<HospitalStuffDto> getAllDoctors(){
        List<HospitalStuff> doctors;
        try {
           doctors = hospitalStuffRepository.getHospitalStuffByDoctorSpecializationIsNotNull();
        }catch (Exception e){
         throw new NoSuchUserException("No one doctors here");
        }
        return HospitalStuffMapper.INSTANCE.toDto(doctors);
    }

    public void appointDoctorToPatient(int doctorId, int patientId){

        HospitalStuff hospitalStuff = hospitalStuffRepository.getHospitalStuffById(doctorId);

        List<Patient> patientList = hospitalStuff.getPatientsList();

        patientList.add(patientRepository.getPatientById(patientId));

        hospitalStuffRepository.save(hospitalStuff);
    }
}
