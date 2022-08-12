package com.khmal.hospital.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;


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
    private LocalDateTime date;

    @Column(name = "appointment_type")
    private String appointmentType;

    @Column(name = "summary")
    private String summary;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "hospital_staff_id", referencedColumnName = "id")
    private HospitalStaff hospitalStaff;

    public Appointment(LocalDateTime date, String appointmentType, String summary, Patient patient, HospitalStaff hospitalStaff) {
        this.date = date;
        this.appointmentType = appointmentType;
        this.summary = summary;
        this.patient = patient;
        this.hospitalStaff = hospitalStaff;
    }

    public enum DoctorAppointment{
        PROCEDURES,
        MEDICATIONS,
        OPERATIONS
    }

    public enum NurseAppointment{
        PROCEDURES,
        MEDICATIONS
    }
}
