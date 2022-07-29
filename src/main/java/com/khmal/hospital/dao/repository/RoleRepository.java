package com.khmal.hospital.dao.repository;

import com.khmal.hospital.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> getRoleById(int id);
}
