package com.khmal.hospital.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private Integer id;

    @OneToOne
    @JoinColumn (name = "username")
    private User user;

    @Column(name = "authority")
    private String roleName;

    public Role(User user, String roleName) {
        this.user = user;
        this.roleName = roleName;
    }
}
