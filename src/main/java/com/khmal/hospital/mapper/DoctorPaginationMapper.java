package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.HospitalStaff;
import com.khmal.hospital.dto.DoctorDto;
import org.springframework.data.domain.Page;

import java.util.function.Function;

public class DoctorPaginationMapper {
    public static Page<DoctorDto> toDto(Page<HospitalStaff> hospitalStuffPage){
        Page<DoctorDto> doctorDtoPage = hospitalStuffPage.map(new Function<HospitalStaff, DoctorDto>() {
            @Override
            public DoctorDto apply(HospitalStaff hospitalStuff) {
                DoctorDto doctorDto = new DoctorDto();
                doctorDto.setId(hospitalStuff.getId());
                doctorDto.setFirstname(hospitalStuff.getFirstname());
                doctorDto.setLastname(hospitalStuff.getLastname());
                doctorDto.setUsername(hospitalStuff.getUsername());
                doctorDto.setDoctorSpecialization(hospitalStuff.getDoctorSpecialization());
                doctorDto.setPatientCount(hospitalStuff.getPatientCount());
                return doctorDto;
            }
        });
        return doctorDtoPage;
    }
}
