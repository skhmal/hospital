package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.HospitalStuff;
import com.khmal.hospital.dto.DoctorDto;
import org.springframework.data.domain.Page;

import java.util.function.Function;

public class DoctorDtoPageMapper {
    public static Page<DoctorDto> toDto(Page<HospitalStuff> hospitalStuffPage){
        Page<DoctorDto> doctorDtoPage = hospitalStuffPage.map(new Function<HospitalStuff, DoctorDto>() {
            @Override
            public DoctorDto apply(HospitalStuff hospitalStuff) {
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
