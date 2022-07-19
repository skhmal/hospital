package com.khmal.hospital.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "hospital_stuff")
public class HospitalStuff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "username")
    private String username;

    @Column(name = "doctor_specialization")
    private String doctorSpecialization;

    @OneToOne
    @JoinColumn(name = "role_id")
    private StuffRole stuffRole;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "doctor_patient",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    @ToString.Exclude
    private List<Patient> patientsList;

    public void addPatientToList(Patient patient){
        if (patientsList == null){
            patientsList = new ArrayList<>();
        }
        patientsList.add(patient);
    }

    enum DoctorSpecialization{
        ALLERGIST,
        ANESTHESIOLOGIST,
        CARDIOLOGIST,
        DERMATOLOGIST,
        ENDOCRINOLOGIST,
        EMERGENCY_MEDICINE_SPECIALIST,
        FAMILY_PHYSICIAN,
        GASTROENTEROLOGIST,
        GYNECOLOGIST,
        HEMATOLOGIST,
        INFECTIOUS_DISEASE_SPECIALIST,
        NEUROLOGIST,
        ONCOLOGIST,
        OPHTHALMOLOGIST,
        PEDIATRICIAN,
        PSYCHIATRISTS,
        SURGEON,
        TRAUMATOLOGIST,
        UROLOGIST
    }

    public HospitalStuff(String firstname, String lastname, String username, String doctorSpecialization, StuffRole stuffRole) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.doctorSpecialization = doctorSpecialization;
        this.stuffRole = stuffRole;
    }
}
