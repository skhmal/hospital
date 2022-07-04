package com.khmal.Hosp.entity;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
//@AttributeOverrides({
//        @AttributeOverride(name="isDeleted", column=@Column(name="is_deleted")),
//        @AttributeOverride(name="deletedDate", column=@Column(name="deleted_date"))
//})
//@MappedSuperclass
public class User extends BaseClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "birthday")
    private LocalDate birthday;

    private String password;

    @Column(name = "enabled")
    private byte enabled;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Role role;

    public User(String firstName, String lastName, LocalDate birthday, String password, byte enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.password = password;
        this.enabled = enabled;
    }

    public User() {

    }
}
