package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.Role;
import com.khmal.hospital.dao.entity.User;
import com.khmal.hospital.dto.RoleDto;
import com.khmal.hospital.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    void toDto() {
        String roleName = "ROLE_DOCTOR";
        String username = "DANIEL";
        User user = new User();
        user.setUsername(username);

        Role role = new Role();
        role.setRoleName(roleName);
        role.setUser(user);

        RoleDto roleDto = roleMapper.toDto(role);

        assertEquals(roleName, roleDto.getRoleName());
        assertEquals(username, roleDto.getUser().getUsername());

    }

    @Test
    void toEntity() {
        String roleName = "ROLE_DOCTOR";
        String username = "DANIEL";
        UserDto user = new UserDto();
        user.setUsername(username);

        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName(roleName);
        roleDto.setUser(user);

        Role role = roleMapper.toEntity(roleDto);

        assertEquals(roleName, role.getRoleName());
        assertEquals(username, role.getUser().getUsername());
    }
}