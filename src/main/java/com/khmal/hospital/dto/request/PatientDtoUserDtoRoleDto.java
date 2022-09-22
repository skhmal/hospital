package com.khmal.hospital.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PatientDtoUserDtoRoleDto {

    @NotBlank(message = "Firstname can't be empty")
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$", message = "First name must contains only letters")
    private String firstName;

    @NotBlank(message = "Lastname can't be empty")
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$", message = "Last name must contains only letters")
    private String lastname;

    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "Password can't be empty")
    private String password;

    @NotNull(message = "Birthday can't be empty")
    @Past(message = "Birthday date is wrong")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private int roleId;
}
