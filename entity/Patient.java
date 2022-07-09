package com.khmal.hospital.entity;

import lombok.*;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;

@ToString
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "patient")
public class Patient extends User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
