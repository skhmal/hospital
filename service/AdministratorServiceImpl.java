package com.khmal.hospital.service;

import com.khmal.hospital.entity.Administrator;
import com.khmal.hospital.entity.User;
import com.khmal.hospital.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdministratorServiceImpl implements AdministratorService{

    private AdministratorRepository administratorRepository;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public Administrator addNewAdministrator(Administrator administrator) {
        return administratorRepository.save(administrator);
    }
}
