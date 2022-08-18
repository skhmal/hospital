package com.khmal.hospital.service;

import com.khmal.hospital.controller.exception.handling.NoSuchUserException;
import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dao.entity.Patient;
import com.khmal.hospital.dao.repository.HospitalStaffRepository;
import com.khmal.hospital.dao.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Methods which work with spring security
 */
@Service
public class SecurityService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);
    private final PatientRepository patientRepository;
    private final HospitalStaffRepository hospitalStaffRepository;


    public SecurityService(PatientRepository patientRepository, HospitalStaffRepository hospitalStaffRepository) {
        this.patientRepository = patientRepository;
        this.hospitalStaffRepository = hospitalStaffRepository;
    }

    /**
     * Get employee (admin, doctor, nurse) id from database by username
     * @param username username
     * @return employee id
     */
    public int getEmployeeId(String username){

        logger.info("Method getEmployeeId started");

        HospitalStaff employee = hospitalStaffRepository.findHospitalStuffByUsername(username).orElseThrow(
                () -> new NoSuchUserException("Employee is not found")
        );

        logger.info("Method getEmployeeId finished");

        return employee.getId();
    }

    /**
     * Get patient id from database by username
     * @param username
     * @return patient id
     */
    public int getPatientId(String username){

        logger.info("Method getPatientId started");

        Patient patient =patientRepository.findPatientByUsername(username).orElseThrow(
                () -> new NoSuchUserException("Patient is not found")
        );

        logger.info("Method getPatientId finished");

        return patient.getId();
    }


}
