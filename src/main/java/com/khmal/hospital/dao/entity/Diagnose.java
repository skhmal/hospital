package com.khmal.hospital.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "diagnose")
public class Diagnose{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "diagnose_date")
    private LocalDate diagnoseDate;

    @Column(name = "edit_date")
    private LocalDate editDate;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private HospitalStaff hospitalStaff;

    public Diagnose(String summary, LocalDate diagnoseDate, Patient patient, HospitalStaff hospitalStaff) {
        this.summary = summary;
        this.diagnoseDate = diagnoseDate;
        this.patient = patient;
        this.hospitalStaff = hospitalStaff;
    }
}
