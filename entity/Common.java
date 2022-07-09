package com.khmal.hospital.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class Common {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected int id;

    @Column(name = "name")
    protected String name;
}
