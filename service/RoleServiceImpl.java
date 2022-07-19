package com.khmal.hospital.service;

import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.RoleDto;
import com.khmal.hospital.dto.UserDto;
import com.khmal.hospital.entity.Role;
import com.khmal.hospital.mapper.RoleMapper;
import com.khmal.hospital.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void addRole(RoleDto roleDto, String roleName) {
        Role role = RoleMapper.INSTANCE.toEntity(roleDto);
        roleRepository.save(role);
    }

    @Override
    public void addRole(RoleDto roleDto) {
        Role role = RoleMapper.INSTANCE.toEntity(roleDto);
        roleRepository.save(role);
    }
}
