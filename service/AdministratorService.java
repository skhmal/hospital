package com.khmal.hospital.service;


import com.khmal.hospital.entity.Administrator;

import java.util.List;

public interface AdministratorService {
    Administrator saveAdmin(Administrator administrator);

    List<Administrator> getAllAdmins();

    void deleteAdmin(Administrator administrator);
}
