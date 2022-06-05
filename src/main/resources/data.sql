



INSERT INTO role (name_role)	VALUES ('ROLE_ADMIN');


INSERT INTO account(last_name_account, name_account, password_account, user_account, id_role) VALUES ('Rodriguez', 'Ruben', '$2a$10$G2A7OIaRgPh2KeMfv39Gt.D8x/aeP63SlSS5AM8.E0UbIyrRS1/ha', 'admin', 1);
INSERT INTO account(last_name_account, name_account, password_account, user_account, id_role) VALUES ('Casquino', 'Gonzalo', '$2a$10$z2i4BbNQWKYfHSslVDpL6uCDaQx6v736epbjMAP9KvSTz7LcO/n2C', 'caski', 1);
INSERT INTO account(last_name_account, name_account, password_account, user_account, id_role) VALUES ('Moyano', 'Nickolas', '$2a$10$lQwqmw0rkn9rwdtNCI9QIuZXARKNuKHJ7D0RIl0gAztj9NS8gXt0i', 'nicko', 1);
INSERT INTO account(last_name_account, name_account, password_account, user_account, id_role) VALUES ('Espejo', 'Alejandro', '$2a$10$4AXUpD4ZCQvaxpnaUNVccu6E5sg955JhkyckTLC0y6oNMZuo7f.0u', 'alej', 1);

INSERT INTO Students(id_student, date_of_admission_student, date_of_birth_student, lastname_student, name_student) VALUES (201815565, '2017-10-15', '2000-09-30', 'Espejo Gonzalez', 'Alejandro Augusto');
INSERT INTO Students(id_student, date_of_admission_student, date_of_birth_student, lastname_student, name_student) VALUES (201612953, '2015-11-18', '1999-01-09', 'Moyano Tumbalobos', 'Nickolas Moises');
INSERT INTO Students(id_student, date_of_admission_student, date_of_birth_student, lastname_student, name_student) VALUES (201814238, '2017-10-17', '2001-05-04', 'Rodriguez Canahuire', 'Ruben Andres');
INSERT INTO Students(id_student, date_of_admission_student, date_of_birth_student, lastname_student, name_student) VALUES (201719761, '2016-10-05', '2000-07-26', 'Casquino Flores', 'Luis Gonzalo');

INSERT INTO Courses (name_course) VALUES ('Fisica');
INSERT INTO Courses (name_course) VALUES ('Fisica II');
INSERT INTO Courses (name_course) VALUES ('Fisica III');
INSERT INTO Courses (name_course) VALUES ('Calculo');
INSERT INTO Courses (name_course) VALUES ('Calculo II');
INSERT INTO Courses (name_course) VALUES ('FAE');
INSERT INTO Courses (name_course) VALUES ('ADAE');
INSERT INTO Courses (name_course) VALUES ('Estadistica Aplicada');
INSERT INTO Courses (name_course) VALUES ('Estadistica Aplicada II');
INSERT INTO Courses (name_course) VALUES ('Matemática Básica');
INSERT INTO Courses (name_course) VALUES ('Taller de Creatividad');

INSERT INTO Teachers(id_teacher, date_of_admission_teacher, date_of_birth_teacher, lastname_teacher, name_teacher) VALUES (85462312, '2005-08-10', '1980-09-30', 'Macedo Lozano', 'Anthony Clinton');
INSERT INTO Teachers(id_teacher, date_of_admission_teacher, date_of_birth_teacher, lastname_teacher, name_teacher) VALUES (62145232, '2002-11-18', '1970-01-09', 'Cerda Garcia', 'Ruben Oscar');
INSERT INTO Teachers(id_teacher, date_of_admission_teacher, date_of_birth_teacher, lastname_teacher, name_teacher) VALUES (96587412, '2001-10-17', '1975-05-04', 'Burga Durango', 'Daniel Wilfredo');
INSERT INTO Teachers(id_teacher, date_of_admission_teacher, date_of_birth_teacher, lastname_teacher, name_teacher) VALUES (42123657, '2005-11-15', '1968-07-26', 'Larios Franco', 'Alfredo Cesar');
INSERT INTO Teachers(id_teacher, date_of_admission_teacher, date_of_birth_teacher, lastname_teacher, name_teacher) VALUES (12123747, '2005-12-15', '1970-04-26', 'Herrera Trujillo', 'Emilio Arturo');

