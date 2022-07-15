package com.khmal.hospital.entity;

import lombok.*;

import javax.persistence.*;


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

    public Administrator(User user) {
        this.user = user;
    }
}
