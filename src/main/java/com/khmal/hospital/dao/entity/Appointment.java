package com.khmal.hospital.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "appointment")
public class Appointment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "appointment_date")
    private LocalDate date;

    @Column(name = "appointment_type")
    private String appointmentType;

    @Column(name = "summary")
    private String summary;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "hospital_stuff_id", referencedColumnName = "id")
    private HospitalStuff hospitalStuff;

    public Appointment(LocalDate date, String appointmentType, String summary, Patient patient, HospitalStuff hospitalStuff) {
        this.date = date;
        this.appointmentType = appointmentType;
        this.summary = summary;
        this.patient = patient;
        this.hospitalStuff = hospitalStuff;
    }

    enum DoctorAppointment{
        PROCEDURES,
        MEDICATIONS,
        OPERATIONS
    }

    enum NurseAppointment{
        PROCEDURES,
        MEDICATIONS
    }
}
