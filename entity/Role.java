package com.khmal.hospital.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "authorities")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private String roleName;

    @OneToOne(mappedBy = "doctorSpecialization")
    private Doctor doctorRole;

    public Role(String username, String roleName) {
        this.username = username;
        this.roleName = roleName;
    }
}
