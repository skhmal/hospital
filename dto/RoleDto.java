package com.khmal.hospital.dto;

import com.khmal.hospital.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoleDto {
    private UserDto user;

    private String roleName;
}
