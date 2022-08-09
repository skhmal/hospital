create database db_hospital;
use db_hospital;

create table role(
    id int not null auto_increment,
    role_name varchar(45) not null ,
    primary key (id)
);

create table hospital_stuff(
    id int not null auto_increment,
    role_id int not null ,
    firstname varchar(45) not null ,
    lastname varchar(45) not null ,
    username varchar(45) not null ,
    doctor_specialization varchar(45) null,
    patient_count int,
    primary key (id),
    foreign key (role_id) references role(id)
);

create table patient(
    id int not null auto_increment,
    firstname varchar(45) not null ,
    lastname varchar(45) not null ,
    birthday date not null ,
    username varchar(45) not null ,
    discharged boolean default false,
    role_id int not null ,
    primary key (id),
    foreign key (role_id) references role(id)
);

create table doctor_patient(
    doctor_id int not null ,
    patient_id int not null ,
    primary key (doctor_id,patient_id),
    foreign key (doctor_id) references hospital_stuff(id),
    foreign key (patient_id) references patient(id)
);

create table appointment(
    id int not null auto_increment,
    appointment_type varchar(45) not null ,
    appointment_date date not null ,
    patient_id int not null ,
    hospital_stuff_id int not null ,
    summary varchar(255) null ,
    primary key (id),
    foreign key (patient_id) references patient(id),
    foreign key (hospital_stuff_id) references hospital_stuff(id)
);

create table diagnose(
    id int not null auto_increment,
    patient_id int not null ,
    doctor_id int not null ,
    diagnose_date date not null ,
    edit_date date null ,
    summary varchar(255) null ,
    primary key (id),
    foreign key (patient_id) references patient(id),
    foreign key (doctor_id) references hospital_stuff(id)
);

create table users(
    username varchar(45) not null ,
    password varchar(45) not null ,
    enabled boolean default true,
    primary key (username)
);

create table authorities(
    username varchar(45) not null ,
    authority varchar(45) not null ,
    foreign key (username) references users(username)
);