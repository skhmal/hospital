package com.khmal.hospital.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment extends BaseClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_type_id", referencedColumnName = "id")
    private AppointmentType appointmentType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    public Appointment(LocalDateTime date, AppointmentType appointmentType, Patient patient, Doctor doctor) {
        this.date = date;
        this.appointmentType = appointmentType;
        this.patient = patient;
        this.doctor = doctor;
    }
}
