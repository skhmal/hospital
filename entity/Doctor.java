package com.khmal.hospital.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "doctor")
public class Doctor  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_specialization_id", referencedColumnName = "id")
    private DoctorSpecialization doctorSpecialization;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public Doctor(DoctorSpecialization doctorSpecialization, User user) {
        this.doctorSpecialization = doctorSpecialization;
        this.user = user;
    }

    public Doctor() {

    }
}
