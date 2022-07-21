package com.khmal.hospital.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDto {
    private String username;

    private String password;

    private Integer enabled;
}
