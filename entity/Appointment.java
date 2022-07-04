package com.khmal.Hosp.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
//@AttributeOverrides({
//        @AttributeOverride(name="isDeleted", column=@Column(name="isDeleted")),
//        @AttributeOverride(name="deleted_date", column=@Column(name="deleted_date"))
//})
public class Appointment extends BaseClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

//    @Column(name = "doctor_id")
//    private int doctorId;
//
//    @Column(name = "patient_id")
//    private int patientId;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appoitment_type_id", referencedColumnName = "id")
    private AppointmentType appointmentType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;
}
