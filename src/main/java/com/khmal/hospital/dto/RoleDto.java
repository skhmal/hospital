package com.khmal.hospital.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RoleDto {
    private Integer id;

    private UserDto user;

    private String username;

    private String roleName;

    public RoleDto(String username, String roleName) {
        this.username = username;
        this.roleName = roleName;
    }
}
