package com.khmal.hospital.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "nurse")
public class Nurse{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Nurse(User user) {
        this.user = user;
    }

    public Nurse() {

    }
}
