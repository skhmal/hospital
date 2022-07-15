package com.khmal.hospital.request;

import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.User;
import lombok.Getter;

@Getter
public class UserDoctorRequest {

    private   Doctor doctor;

    private   User user;
}
