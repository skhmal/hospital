package com.khmal.hospital.dao.repository;


import com.khmal.hospital.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> getUserByUsername(String username);
}
