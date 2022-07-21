package com.khmal.hospital.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoleDto {
    private Integer id;

    private UserDto user;

    private String roleName;
}
