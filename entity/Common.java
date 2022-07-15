package com.khmal.hospital.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class Common {

    @Column(name = "name")
    protected String name;
}
