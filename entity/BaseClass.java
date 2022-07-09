package com.khmal.hospital.entity;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "is_deleted")
    Integer isDeleted;

    @Column(name = "deleted_date")
    private LocalDate deletedDate;
}
