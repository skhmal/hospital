use db_hospital1;

create table if not exists role
(
    id        int         not null auto_increment,
    role_name varchar(45) not null,
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

create table if not exists users
(
    username varchar(45)  not null,
    password varchar(100) not null,
    enabled  boolean default true,
    primary key (username)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

create table if not exists authorities
(
    id int not null auto_increment,
    username  varchar(45) not null,
    authority varchar(45) not null,
    primary key (id),
    unique key (username),
    foreign key (username) references users (username)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

create table if not exists hospital_staff
(
    id                    int         not null auto_increment,
    role_id               int         not null,
    firstname             varchar(45) not null,
    lastname              varchar(45) not null,
    username              varchar(45) not null,
    doctor_specialization varchar(45) null,
    patient_count         int         not null default 0,
    unique key (username),
    primary key (id),
    foreign key (role_id) references role (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

create table if not exists patient
(
    id         int         not null auto_increment,
    firstname  varchar(45) not null,
    lastname   varchar(45) not null,
    birthday   date        not null,
    username   varchar(45) not null,
    discharged boolean default false,
    role_id    int         not null,
    unique key (username),
    primary key (id),
    foreign key (role_id) references role (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

create table if not exists doctor_patient
(
    doctor_id  int not null,
    patient_id int not null,
    primary key (doctor_id, patient_id),
    foreign key (doctor_id) references hospital_staff (id),
    foreign key (patient_id) references patient (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

create table if not exists appointment
(
    id                int          not null auto_increment,
    appointment_type  varchar(45)  not null,
    appointment_date  datetime     not null,
    patient_id        int          not null,
    hospital_staff_id int          not null,
    summary           varchar(255) null,
    primary key (id),
    foreign key (patient_id) references patient (id),
    foreign key (hospital_staff_id) references hospital_staff (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

create table if not exists diagnose
(
    id            int          not null auto_increment,
    patient_id    int          not null,
    doctor_id     int          not null,
    diagnose_date date         not null,
    edit_date     date         null,
    summary       varchar(255) null,
    primary key (id),
    foreign key (patient_id) references patient (id),
    foreign key (doctor_id) references hospital_staff (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

