package com.khmal.hospital.entity;

import javax.persistence.*;

@Entity
@Table(name = "doctor")
public class Doctor extends User {

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
}
