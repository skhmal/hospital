package com.khmal.hospital.service;

import com.khmal.hospital.dao.entity.Diagnose;
import com.khmal.hospital.dao.repository.DiagnoseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnoseServiceImpl implements DiagnoseService{

    private DiagnoseRepository diagnoseRepository;

    @Autowired
    public DiagnoseServiceImpl(DiagnoseRepository diagnoseRepository) {
        this.diagnoseRepository = diagnoseRepository;
    }


    @Override
    public Diagnose saveDiagnose(Diagnose diagnose) {
        return diagnoseRepository.save(diagnose);
    }

    @Override
    public List<Diagnose> getAllDiagnoses() {
        return diagnoseRepository.findAll();
    }

    @Override
    public void deleteDiagnose(Diagnose diagnose) {
        diagnoseRepository.delete(diagnose);
    }
}
