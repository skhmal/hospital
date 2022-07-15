package com.khmal.hospital.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@NoArgsConstructor
@ToString
@Table(name = "doctor")
public class Doctor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "doctor_specialization_id", referencedColumnName = "id")
    private DoctorSpecialization doctorSpecialization;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userDoctor;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "doctor_patient",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    @JsonManagedReference
    @ToString.Exclude
    private List<Patient> patientsList;

    public void addPatientToPatientList(Patient patient){
        if (patientsList == null){
            patientsList = new ArrayList<>();
        }
        patientsList.add(patient);
    }

    public static final String ROLE = "ROLE_DOCTOR";

    public Doctor(DoctorSpecialization doctorSpecialization, User userDoctor) {
        this.doctorSpecialization = doctorSpecialization;
        this.userDoctor = userDoctor;
    }

    public Doctor(User user) {
        this.userDoctor = user;
    }
}
