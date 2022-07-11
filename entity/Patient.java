package com.khmal.hospital.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User userPatient;

    public static final String ROLE = "ROLE_PATIENT";

    public Patient(User userPatient) {
        this.userPatient = userPatient;
    }

    public Patient() {
    }
}
