package com.khmal.hospital.entity;



import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class BaseClass {

    @Column(name = "is_deleted")
    short isDeleted;

    @Column(name = "deleted_date")
    private LocalDate deletedDate;
}
