package com.khmal.hospital.dao.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "hospital_staff")
public class HospitalStaff {

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
    private StaffRole staffRole;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "doctor_patient",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    @ToString.Exclude
    private List<Patient> patientsList;

    @Column(name = "patient_count")
    private int patientCount;

    public enum DoctorSpecialization{
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

    public HospitalStaff(String firstname, String lastname, String username, String doctorSpecialization, StaffRole staffRole) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.doctorSpecialization = doctorSpecialization;
        this.staffRole = staffRole;
    }
    public HospitalStaff(int id,String firstname, String lastname, String username, String doctorSpecialization, StaffRole staffRole) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.doctorSpecialization = doctorSpecialization;
        this.staffRole = staffRole;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HospitalStaff that = (HospitalStaff) o;
        return lastname != null && Objects.equals(lastname, that.lastname);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
