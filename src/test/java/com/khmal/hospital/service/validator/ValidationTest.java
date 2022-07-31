package com.khmal.hospital.service.validator;

import com.khmal.hospital.service.exception_handling.IncorrectDateException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidationTest {

    @InjectMocks
    private Validation validation;

    @Test
    void checkHospitalStuffId() {
    }

    @Test
    void checkPatientId() {
    }

    @Test
    void checkAppointmentDateForHospitalStuff() {
    }

    @Test
    void checkRoleInDataBase() {
    }

    @Test
    void checkDoctorSpecializationPositiveCase(){
        String doctorSpecializationName = "SURGEON";

        assertTrue(validation.checkDoctorSpecialization(doctorSpecializationName));
    }

    @Test
    void checkDoctorSpecializationNegativeCase(){
        String badDoctorSpecializationName = "HOME";

        assertThrows(IncorrectDateException.class,
                () -> validation.checkDoctorSpecialization(badDoctorSpecializationName));
    }
}