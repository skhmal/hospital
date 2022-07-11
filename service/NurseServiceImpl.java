package com.khmal.hospital.service;

import com.khmal.hospital.entity.Nurse;
import com.khmal.hospital.repository.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NurseServiceImpl implements NurseService{

    private final NurseRepository nurseRepository;

    @Autowired
    public NurseServiceImpl(NurseRepository nurseRepository) {
        this.nurseRepository = nurseRepository;
    }

    @Override
    public Nurse addNewNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }
}
