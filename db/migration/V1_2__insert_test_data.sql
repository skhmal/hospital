use db_hospital1;

insert into role(role_name)
values ('ROLE_ADMINISTRATOR'),
       ('ROLE_NURSE'),
       ('ROLE_DOCTOR'),
       ('ROLE_PATIENT');

insert into patient(firstname, lastname, birthday, username, discharged, role_id)
values ('Thomas', 'Anderson', '1971-09-13', 'neo', false, 4),
       ('Donald', 'Trump', '1950-01-23', 'donald', false, 4),
       ('Pawel', 'Grabowski', '1979-09-13', 'pawel', false, 4),
       ('Johny', 'Mnemonic', '1989-11-29', 'jm', false, 4),
       ('Kubus', 'Puchatek', '1929-10-02', 'puchatek', false, 4),
       ('Kapitan', 'Bomba', '1995-08-15', 'bomba', false, 4);

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

insert into hospital_staff(role_id, firstname, lastname, username, doctor_specialization)
values (3, 'Alphonse', 'Mephesto', 'am', 'ALLERGIST'),
       (3, 'Dr', 'Gouache', 'gouache', 'UROLOGIST'),
       (3, 'Adrian', 'Spejson', 'spejson', 'NEUROLOGIST'),
       (3, 'John', 'Zoidberg', 'zoidberg', 'ONCOLOGIST'),
       (3, 'Jaroslaw', 'Kacz', 'kacz', 'PEDIATRICIAN'),
       (3, 'Rafal', 'Tracz', 'rafal', 'CARDIOLOGIST'),
       (1, 'Sergei', 'Serg', 'serg', null),
       (2, 'Barbara', 'Basia', 'basia', null);