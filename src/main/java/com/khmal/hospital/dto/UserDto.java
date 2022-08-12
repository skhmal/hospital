package com.khmal.hospital.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "Field username must not be empty")
    private String username;

    @NotBlank(message = "Field password must not be empty")
    private String password;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
