package com.khmal.hospital.webb;

import com.khmal.hospital.dto.PatientDto;
import com.khmal.hospital.dto.RoleDto;
import com.khmal.hospital.dto.UserDto;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PatientUserRole {
    PatientDto patientDto;
    UserDto userDto;
    RoleDto roleDto;
}
