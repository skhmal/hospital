package com.khmal.hospital.service;

import com.khmal.hospital.entity.Nurse;

import java.util.List;

public interface NurseService {
    Nurse saveNurse(Nurse nurse);
    List<Nurse> getAllNurses();
    void deleteNurse(Nurse nurse);
}
