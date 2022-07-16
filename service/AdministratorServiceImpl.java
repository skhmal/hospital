package com.khmal.hospital.service;

import com.khmal.hospital.entity.Administrator;
import com.khmal.hospital.repository.AdministratorRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService{

    private final AdministratorRepository administratorRepository;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public Administrator saveAdmin(@NonNull Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    @Override
    public List<Administrator> getAllAdmins() {
        return administratorRepository.findAll();
    }

    @Override
    public void deleteAdmin(Administrator administrator) {
        if (administrator != null){
            administratorRepository.delete(administrator);
        }
    }
}
