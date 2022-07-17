package com.khmal.hospital.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User userPatient;

@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "doctor_patient",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    @ToString.Exclude
    @JsonIgnore
    private List<Doctor> doctorsList;

    public void addDoctorToDoctorList(Doctor doctor){
        if (doctorsList == null){
            doctorsList = new ArrayList<>();
        }
        doctorsList.add(doctor);
    }

    public static final String ROLE = "ROLE_PATIENT";

    public Patient(User userPatient) {
        this.userPatient = userPatient;
    }
}
