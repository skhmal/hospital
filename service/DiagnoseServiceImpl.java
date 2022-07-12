package com.khmal.hospital.service;

import com.khmal.hospital.entity.Diagnose;
import com.khmal.hospital.repository.DiagnoseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiagnoseServiceImpl implements DiagnoseService{

    private DiagnoseRepository diagnoseRepository;

    @Autowired
    public DiagnoseServiceImpl(DiagnoseRepository diagnoseRepository) {
        this.diagnoseRepository = diagnoseRepository;
    }


    @Override
    public Diagnose addNewDiagnose(Diagnose diagnose) {
        return diagnoseRepository.save(diagnose);
    }
}
