package com.khmal.hospital.viewmodel;

import com.khmal.hospital.entity.Doctor;
import com.khmal.hospital.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDoctorViewModel {

    public Doctor doctor;

    public User user;
}
