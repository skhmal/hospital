package com.khmal.hospital.service;

import com.khmal.hospital.entity.AppointmentType;
import com.khmal.hospital.repository.AppointmentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentTypeServiceImpl implements AppointmentTypeService {

    private AppointmentTypeRepository appointmentTypeRepository;

    @Autowired
    public AppointmentTypeServiceImpl(AppointmentTypeRepository appointmentTypeRepository) {
        this.appointmentTypeRepository = appointmentTypeRepository;
    }

    @Override
    public AppointmentType getAppoitmentTypeById(Integer id) {
        return appointmentTypeRepository.getAppointmentTypeById(id);
    }
}
