package com.khmal.hospital.dto;

import com.khmal.hospital.service.validator.CreateOrUpdateMarker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RoleDto {

    @Null(groups = CreateOrUpdateMarker.OnCreate.class)
    @NotNull(groups = CreateOrUpdateMarker.OnUpdate.class)
    private Integer id;

    @NotNull
    private UserDto user;

    @NotBlank(message = "Field username must not be empty")
    private String username;

    @NotBlank(message = "Field role must not be empty")
    private String roleName;

    public RoleDto(String username, String roleName) {
        this.username = username;
        this.roleName = roleName;
    }
}
