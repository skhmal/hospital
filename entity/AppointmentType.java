package com.khmal.hospital.entity;

import javax.persistence.*;

@Entity
@Table(name = "appoitment_type")
public class AppointmentType extends Common{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
}
