package com.khmal.hospital.dto.webb;

import com.khmal.hospital.dto.HospitalStuffDto;
import com.khmal.hospital.dto.RoleDto;
import com.khmal.hospital.dto.UserDto;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HospitalStuffDtoUserDtoRoleDto {
    HospitalStuffDto hospitalStuffDto;
    UserDto userDto;
    RoleDto roleDto;
}
