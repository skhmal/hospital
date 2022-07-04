package com.khmal.Hosp.entity;

import javax.persistence.*;

@Entity
@Table(name = "patient")
public class Patient extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Patient(User user) {
        this.user = user;
    }

    public Patient() {

    }
}
