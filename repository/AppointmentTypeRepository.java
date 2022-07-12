package com.khmal.hospital.repository;

import com.khmal.hospital.entity.AppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentTypeRepository extends JpaRepository<AppointmentType, Integer> {
    AppointmentType getAppointmentTypeById(Integer id);
}
