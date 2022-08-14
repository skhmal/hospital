package com.khmal.hospital.service.exception_handling;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IncorrectDataTest {

    @Test
    void createIncorrectData(){
        IncorrectData incorrectData = new IncorrectData();
        assertNotNull(incorrectData);
    }
}