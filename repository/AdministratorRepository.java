package com.khmal.hospital.repository;

import com.khmal.hospital.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

}