package com.khmal.hospital.dao.repository;

import com.khmal.hospital.dao.entity.StaffRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRoleRepository extends JpaRepository<StaffRole, Integer> {
    Optional<StaffRole> getStuffRoleById(int id);
}
