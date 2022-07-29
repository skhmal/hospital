package com.khmal.hospital.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "Field username must not be empty")
    private String username;

    @NotBlank(message = "Field password must not be empty")
    private String password;

    @Digits(integer = 1, fraction = 0, message = "Field enabled must be digit")
    @NotNull(message = "Field enabled must not be empty")
    private Integer enabled;

    public UserDto(String username, String password, Integer enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
}
