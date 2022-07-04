package com.khmal.Hosp.entity;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private String authority;


}
