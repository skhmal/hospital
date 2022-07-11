package com.khmal.hospital.service;

import com.khmal.hospital.entity.DoctorSpecialization;
import com.khmal.hospital.repository.DoctorSpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorSpecializationServiceImpl implements DoctorSpecializationService{
    private DoctorSpecializationRepository doctorSpecializationRepository;

    @Autowired
    public DoctorSpecializationServiceImpl(DoctorSpecializationRepository doctorSpecializationRepository) {
        this.doctorSpecializationRepository = doctorSpecializationRepository;
    }

    @Override
    public DoctorSpecialization getDoctorSpecializationById(Integer id) {
        DoctorSpecialization doctorSpecialization = doctorSpecializationRepository.getDoctorSpecializationById(id);
        return doctorSpecialization;
    }
}
