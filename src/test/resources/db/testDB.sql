-- create tables for test data base
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
    foreign key (username) references users(username),
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
    foreign key (username) references users(username),
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

-- insert data into test data base

insert into role(id, role_name)
values (1, 'ROLE_ADMINISTRATOR'),
       (2, 'ROLE_NURSE'),
       (3, 'ROLE_DOCTOR'),
       (4,'ROLE_PATIENT');

insert into users(username, password, enabled)
values ('neo', '{noop}neo', 1),
       ('am', '{noop}am', 1),
       ('serg', '{noop}serg', 1),
       ('basia', '{noop}basia', 1),
       ('donald', '{noop}donald', 1),
       ('pawel', '{noop}pawel', 1),
       ('jm', '{noop}jm', 1),
       ('gouache', '{noop}gouache', 1),
       ('puchatek', '{noop}puchatek', 1),
       ('bomba', '{noop}bomba', 1),
       ('spejson', '{noop}spejson', 1),
       ('zoidberg', '{noop}zoidberg', 1),
       ('kacz', '{noop}kacz', 1),
       ('rafal', '{noop}rafal', 1);

insert into authorities(username, authority)
VALUES ('neo', 'ROLE_PATIENT'),
       ('am', 'ROLE_DOCTOR'),
       ('serg', 'ROLE_ADMINISTRATOR'),
       ('basia', 'ROLE_NURSE'),
       ('donald', 'ROLE_PATIENT'),
       ('pawel', 'ROLE_PATIENT'),
       ('jm', 'ROLE_PATIENT'),
       ('gouache', 'ROLE_DOCTOR'),
       ('puchatek', 'ROLE_PATIENT'),
       ('bomba', 'ROLE_PATIENT'),
       ('spejson', 'ROLE_DOCTOR'),
       ('zoidberg', 'ROLE_DOCTOR'),
       ('kacz', 'ROLE_DOCTOR'),
       ('rafal', 'ROLE_DOCTOR');

insert ignore into patient(firstname, lastname, birthday, username, discharged, role_id)
values ('Thomas', 'Anderson', '1971-09-13', 'neo', false, 4),
       ('Donald', 'Trump', '1950-01-23', 'donald', false, 4),
       ('Pawel', 'Grabowski', '1979-09-13', 'pawel', false, 4),
       ('Johny', 'Mnemonic', '1989-11-29', 'jm', false, 4),
       ('Kubus', 'Puchatek', '1929-10-02', 'puchatek', false, 4),
       ('Kapitan', 'Bomba', '1995-08-15', 'bomba', false, 4);

insert ignore into hospital_staff(role_id, firstname, lastname, username, doctor_specialization)
values (3, 'Alphonse', 'Mephesto', 'am', 'ALLERGIST'),
       (3, 'Dr', 'Gouache', 'gouache', 'UROLOGIST'),
       (3, 'Adrian', 'Spejson', 'spejson', 'NEUROLOGIST'),
       (3, 'John', 'Zoidberg', 'zoidberg', 'ONCOLOGIST'),
       (3, 'Jaroslaw', 'Kacz', 'kacz', 'PEDIATRICIAN'),
       (3, 'Rafal', 'Tracz', 'rafal', 'CARDIOLOGIST'),
       (1, 'Sergei', 'Serg', 'serg', null),
       (2, 'Barbara', 'Basia', 'basia', null);