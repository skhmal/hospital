package com.khmal.hospital.mapper;

import com.khmal.hospital.dao.entity.StaffRole;
import com.khmal.hospital.dto.StaffRoleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import resources.Helper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StaffRoleMapperTest {

    @Autowired
    private StaffRoleMapper staffRoleMapper;

    private final StaffRole staffRole = Helper.getStaffRole();
    private final StaffRoleDto staffRoleDto = Helper.getStaffRoleDto();

    @Test
    void toDto() {
        StaffRoleDto staffRoleDtoTest = staffRoleMapper.toDto(staffRole);

        assertEquals(staffRole.getRoleName(), staffRoleDtoTest.getRoleName());
    }

    @Test
    void toEntity() {
        StaffRole staffRoleTest = staffRoleMapper.toEntity(staffRoleDto);

        assertEquals(staffRoleDto.getRoleName(), staffRoleTest.getRoleName());
    }

    @Test
    void testToEntity() {
        List<StaffRoleDto> staffRoleDtoList = new ArrayList<>();
        staffRoleDtoList.add(staffRoleDto);

        List<StaffRole> staffRoleList = staffRoleMapper.toEntity(staffRoleDtoList);

        assertEquals(staffRoleDto.getRoleName(), staffRoleList.get(0).getRoleName());
    }

    @Test
    void testToDto() {
        List<StaffRole> staffRoleList = new ArrayList<>();
        staffRoleList.add(staffRole);

        List<StaffRoleDto> staffRoleDtoList = staffRoleMapper.toDto(staffRoleList);

        assertEquals(staffRole.getRoleName(), staffRoleDtoList.get(0).getRoleName());
    }
}