package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.Diagnose;
import com.khmal.hospital.dto.DiagnoseDto;
import org.springframework.data.domain.Page;

import java.util.function.Function;

public class DiagnosePaginationMapper {

    public static Page<DiagnoseDto> toDto(Page<Diagnose> diagnoses){
        Page<DiagnoseDto> diagnoseDtoPage = diagnoses.map(new Function<Diagnose, DiagnoseDto>() {
            @Override
            public DiagnoseDto apply(Diagnose diagnose) {

                DiagnoseDto diagnoseDto = new DiagnoseDto();

                diagnoseDto.setId(diagnose.getId());
                diagnoseDto.setDiagnoseDate(diagnose.getDiagnoseDate());
                diagnoseDto.setSummary(diagnose.getSummary());
                diagnoseDto.setPatient(
                        PatientMapper.INSTANCE.toDto(diagnose.getPatient())
                );
                diagnoseDto.setHospitalStaff(
                        HospitalStuffMapper.INSTANCE.toDto(diagnose.getHospitalStaff())
                );
                diagnoseDto.setEditDate(diagnose.getEditDate());

                return diagnoseDto;
            }
        });
        return diagnoseDtoPage;
    }
}
