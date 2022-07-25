package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.Diagnose;

import java.util.List;


public interface DiagnoseService {
    Diagnose saveDiagnose(Diagnose diagnose);

    List<Diagnose> getAllDiagnoses();

    void deleteDiagnose(Diagnose diagnose);
}
