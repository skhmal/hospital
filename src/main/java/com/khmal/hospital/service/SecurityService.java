package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.repository.HospitalStaffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import com.khmal.hospital.service.exception_handling.NoSuchUserException;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final PatientRepository patientRepository;
    private final HospitalStaffRepository hospitalStaffRepository;


    public SecurityService(PatientRepository patientRepository, HospitalStaffRepository hospitalStaffRepository) {
        this.patientRepository = patientRepository;
        this.hospitalStaffRepository = hospitalStaffRepository;
    }

    public int getEmployeeId(String username){

        HospitalStaff employee = hospitalStaffRepository.findHospitalStuffByUsername(username).orElseThrow(
                () -> new NoSuchUserException("Employee is not found")
        );

        return employee.getId();
    }

    public int getPatientId(String username){

        Patient patient =patientRepository.findPatientByUsername(username).orElseThrow(
                () -> new NoSuchUserException("Patient is not found")
        );

        return patient.getId();
    }


}
