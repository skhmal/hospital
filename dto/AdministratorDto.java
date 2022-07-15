package com.khmal.hospital.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdministratorDto {

    public static class User{
        String username;
        String firstName;
        String lastName;
        LocalDate birthday;
        String password;
        byte enabled;
    }

}
