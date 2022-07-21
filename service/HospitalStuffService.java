package com.khmal.hospital.service;

import com.khmal.hospital.dto.HospitalStuffDto;
import com.khmal.hospital.entity.HospitalStuff;
import com.khmal.hospital.entity.Role;
import com.khmal.hospital.entity.User;
import com.khmal.hospital.mapper.HospitalStuffMapper;
import com.khmal.hospital.mapper.RoleMapper;

import com.khmal.hospital.mapper.UserMapper;
import com.khmal.hospital.repository.HospitalStuffRepository;
import com.khmal.hospital.repository.RoleRepository;
import com.khmal.hospital.repository.UserRepository;
import com.khmal.hospital.dto.webb.HospitalStuffDtoUserDtoRoleDto;
import org.springframework.stereotype.Service;

@Service
public class HospitalStuffService {

    private final HospitalStuffRepository hospitalStuffRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public HospitalStuffService(HospitalStuffRepository hospitalStuffRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.hospitalStuffRepository = hospitalStuffRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

   public HospitalStuffDto addNewEmployee(HospitalStuffDtoUserDtoRoleDto hospitalStuffDtoUserDtoRoleDto) {
        HospitalStuff hospitalStuff =
                HospitalStuffMapper.INSTANCE.toEntity(hospitalStuffDtoUserDtoRoleDto.getHospitalStuffDto());

        hospitalStuffRepository.save(hospitalStuff);

        User user = UserMapper.INSTANCE.toEntity(hospitalStuffDtoUserDtoRoleDto.getUserDto());

        userRepository.save(user);

        Role role = RoleMapper.INSTANCE.toEntity(hospitalStuffDtoUserDtoRoleDto.getRoleDto());

        roleRepository.save(role);

        return hospitalStuffDtoUserDtoRoleDto.getHospitalStuffDto();
    }

    public void deleteEmployee(HospitalStuffDto hospitalStuffDto){
        hospitalStuffRepository.delete(HospitalStuffMapper.INSTANCE.toEntity(hospitalStuffDto));
    }


}
