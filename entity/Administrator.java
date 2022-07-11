package com.khmal.hospital.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Setter
@ToString
@Entity
@Table(name = "administrator")
public class Administrator{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "user_id", referencedColumnName = "id")
   private User user;

   public static final String ROLE = "ROLE_ADMINISTRATOR";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Administrator that = (Administrator) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Administrator(User user) {
        this.user = user;
    }
}
