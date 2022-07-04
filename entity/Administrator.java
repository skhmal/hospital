package com.khmal.Hosp.entity;

import javax.persistence.*;

@Entity
@Table(name = "administrator")
//@AttributeOverrides({
//        @AttributeOverride(name="isDeleted", column=@Column(name="isDeleted")),
//        @AttributeOverride(name="deleted_date", column=@Column(name="deleted_date"))
//})
public class Administrator extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;


   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "user_id", referencedColumnName = "id")
   private User user;
}
