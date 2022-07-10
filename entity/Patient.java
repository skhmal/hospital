package com.khmal.hospital.entity;

import javax.persistence.*;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User userPatient;

    private int permission = 1;

    public Patient(User userPatient) {
        this.userPatient = userPatient;
    }

    public Patient() {

    }

    public int getPermission() {
        return permission;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserPatient() {
        return userPatient;
    }

    public void setUserPatient(User user) {
        this.userPatient = user;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", userPatient=" + userPatient +
                '}';
    }
}
