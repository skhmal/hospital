package com.khmal.hospital.service;

import com.khmal.hospital.dao.repository.HospitalStuffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.dao.repository.RoleRepository;
import com.khmal.hospital.dao.repository.UserRepository;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class SecurityService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final HospitalStuffRepository hospitalStuffRepository;


    public SecurityService(RoleRepository roleRepository, UserRepository userRepository, PatientRepository patientRepository, HospitalStuffRepository hospitalStuffRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.hospitalStuffRepository = hospitalStuffRepository;
    }

    public Object getUserDetails(Principal principal){
        String username = principal.getName();
        Object result  = null;

        if (hospitalStuffRepository.findHospitalStuffByUsername(principal.getName()).isPresent()){
            result = hospitalStuffRepository.findHospitalStuffByUsername(username)
                    .orElseThrow(() -> new NoSuchUserException("Employee is not found! Object"));
        }else if(patientRepository.findPatientByUsername(username).isPresent()){
            result = patientRepository.findPatientByUsername(username)
                    .orElseThrow(() -> new NoSuchUserException("Patient is not found! Object"));
        }else{
            throw new NoSuchUserException("User is not found! Object");
        }


        return result;
    }


}
