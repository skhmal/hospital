package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dto.DoctorDto;
import org.springframework.data.domain.Page;

public class DoctorPaginationMapper {
    public static Page<DoctorDto> toDto(Page<HospitalStaff> hospitalStuffPage) {
        return hospitalStuffPage.map(doctor ->
                new DoctorDto(
                        doctor.getId(),
                        doctor.getFirstname(),
                        doctor.getLastname(),
                        doctor.getUsername(),
                        doctor.getDoctorSpecialization(),
                        doctor.getPatientCount()
                ));
    }
}
