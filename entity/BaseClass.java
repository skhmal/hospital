package com.khmal.Hosp.entity;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "is_deleted")
    boolean isDeleted;

    @Column(name = "deleted_date")
    private LocalDate deletedDate;
}
