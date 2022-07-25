package com.khmal.hospital.dto.request;

import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.RoleDto;
import com.khmal.hospital.dto.UserDto;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PatientDtoUserDtoRoleDto {
    PatientDto patientDto;
    UserDto userDto;
    RoleDto roleDto;
}
