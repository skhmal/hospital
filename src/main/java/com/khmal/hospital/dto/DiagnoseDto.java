package com.khmal.hospital.dto;

import com.khmal.hospital.dao.entity.HospitalStuff;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class DiagnoseDto {

    private int id;

    private String summary;

    private LocalDate diagnoseDate;

    private PatientDto patient;

    private HospitalStuffDto hospitalStuff;
}
