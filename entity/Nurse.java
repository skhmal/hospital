package com.khmal.Hosp.entity;

import javax.persistence.*;

@Entity
@Table(name = "nurse")
public class Nurse extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
