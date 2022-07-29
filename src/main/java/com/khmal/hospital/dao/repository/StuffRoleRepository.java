package com.khmal.hospital.dao.repository;

import com.khmal.hospital.dao.entity.StuffRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StuffRoleRepository extends JpaRepository<StuffRole, Integer> {
    Optional<StuffRole> getStuffRoleById(int id);
}
