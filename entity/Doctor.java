package com.khmal.hospital.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "doctor")
public class Doctor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(optional = false)
    @JoinColumn(name = "doctor_specialization_id", referencedColumnName = "id")
    private DoctorSpecialization doctorSpecialization;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userDoctor;

    public static final String ROLE = "ROLE_DOCTOR";

    public Doctor(DoctorSpecialization doctorSpecialization, User userDoctor) {
        this.doctorSpecialization = doctorSpecialization;
        this.userDoctor = userDoctor;
    }

    public Doctor() {
    }

    public Doctor(User user) {
        this.userDoctor = user;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorSpecialization=" + doctorSpecialization +
                ", userDoctor=" + userDoctor +
                '}';
    }
}
