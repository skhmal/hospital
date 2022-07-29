package com.khmal.hospital.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "users")
public class User {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Integer enabled;

    public User(String username, String password, Integer enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public User(String sh, int i) {
    }
}
