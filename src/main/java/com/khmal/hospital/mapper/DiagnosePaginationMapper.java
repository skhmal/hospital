package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.Diagnose;
import com.khmal.hospital.dto.DiagnoseDto;
import org.springframework.data.domain.Page;

public class DiagnosePaginationMapper {
    public static Page<DiagnoseDto> toDto(Page<Diagnose> diagnoses) {
        return diagnoses.map(diagnose -> new DiagnoseDto(
                diagnose.getId(),
                diagnose.getSummary(),
                diagnose.getDiagnoseDate(),
                diagnose.getEditDate(),
                PatientMapper.INSTANCE.toDto(diagnose.getPatient()),
                HospitalStuffMapper.INSTANCE.toDto(diagnose.getHospitalStaff())));
    }
}
