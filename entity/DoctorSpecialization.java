package com.khmal.hospital.entity;

import javax.persistence.*;

@Entity
@Table(name = "doctor_specialization")
public class DoctorSpecialization extends Common{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(mappedBy = "doctorSpecialization", cascade = CascadeType.ALL)
    private Doctor doctor;
}
