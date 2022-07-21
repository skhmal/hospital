package com.khmal.hospital.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
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

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "hospital_stuff_id", referencedColumnName = "id")
    private HospitalStuff hospitalStuff;


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
