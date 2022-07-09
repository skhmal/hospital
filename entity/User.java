package com.khmal.hospital.entity;

import lombok.*;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "users")
@Log4j2
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseClass {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Transient
    private String password;

    @Column(name = "enabled")
    private byte enabled;
}
