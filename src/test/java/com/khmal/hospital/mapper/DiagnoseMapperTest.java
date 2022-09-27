package com.khmal.hospital.mapper;

import com.khmal.hospital.Helper;
import com.khmal.hospital.dao.entity.Diagnose;
import com.khmal.hospital.dto.DiagnoseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DiagnoseMapperTest {

    @Autowired
    private DiagnoseMapper diagnoseMapper;

    private final Diagnose diagnose = Helper.getDiagnose();
    private final DiagnoseDto diagnoseDto = Helper.getDiagnoseDto();

    @Test
    void toDto() {
        DiagnoseDto dto = diagnoseMapper.toDto(diagnose);

        assertEquals(diagnose.getDiagnoseDate(), dto.getDiagnoseDate());
        assertEquals(diagnose.getSummary(), dto.getSummary());
        assertEquals(diagnose.getPatient().getFirstname(), dto.getPatient().getFirstname());
    }

    @Test
    void toEntity() {
        Diagnose diagnoseTest = diagnoseMapper.toEntity(diagnoseDto);

        assertEquals(diagnoseDto.getHospitalStaff().getLastname(), diagnoseTest.getHospitalStaff().getLastname());
        assertEquals(diagnoseDto.getSummary(), diagnoseTest.getSummary());
    }

    @Test
    void testToEntity() {
        List<DiagnoseDto> diagnoseDtoList = new ArrayList<>();
        diagnoseDtoList.add(diagnoseDto);

        List<Diagnose> diagnoseList = diagnoseMapper.toEntity(diagnoseDtoList);

        assertEquals(diagnoseDto.getPatient().getBirthday(), diagnoseList.get(0).getPatient().getBirthday());
        assertEquals(diagnoseDto.getHospitalStaff().getFirstname(), diagnoseList.get(0).getHospitalStaff().getFirstname());
    }

    @Test
    void testToDto() {
        List<Diagnose> diagnoseList = new ArrayList<>();
        diagnoseList.add(diagnose);

        List<DiagnoseDto> diagnoseDtoList = diagnoseMapper.toDto(diagnoseList);

        assertEquals(diagnose.getDiagnoseDate(), diagnoseDtoList.get(0).getDiagnoseDate());
        assertEquals(diagnose.getSummary(), diagnoseDtoList.get(0).getSummary());
    }
}