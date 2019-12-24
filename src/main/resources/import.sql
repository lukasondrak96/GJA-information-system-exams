-- Passwords are 'testtest'
INSERT INTO TEACHER (first_name, last_name, degrees_before_name, degrees_behind_name, email, password, status) VALUES ('Lukas','Ondrak','Bc.', '', 'a@a', '$2a$10$n9ar.RZ67AiLyrwe8P.D4Os5OBIYAXj0owTlvYck3sMSy8QgGBtoG', 'VERIFIED');
INSERT INTO TEACHER (first_name, last_name, degrees_before_name, degrees_behind_name, email, password, status) VALUES ('Lukas','Novak','Bc.', 'PhD.', 'b@b', '$2a$10$n9ar.RZ67AiLyrwe8P.D4Os5OBIYAXj0owTlvYck3sMSy8QgGBtoG', 'VERIFIED');
INSERT INTO TEACHER (first_name, last_name, degrees_before_name, degrees_behind_name, email, password, status) VALUES ('Lukas','Dvorak','Bc.', '', 'c@c', '$2a$10$n9ar.RZ67AiLyrwe8P.D4Os5OBIYAXj0owTlvYck3sMSy8QgGBtoG', 'VERIFIED');
INSERT INTO TEACHER (first_name, last_name, degrees_before_name, degrees_behind_name, email, password, status) VALUES ('Tomas','Libal','Ing.', 'PhD.', 'tomas.libal123@gmail.com', '$2a$10$4tV046h0ZEjMLrQeFryA2O6v.DddMpph.OwVT.2aD2H9U2CYNblIe', 'VERIFIED');


INSERT INTO ROLE (id_role, role_name, role_desc) VALUES (1,'LOGGED_IN_USER','LOGGED_IN_USER');

INSERT INTO TEACHER_HAS_ROLE (email, id_role) VALUES ('a@a', 1);
INSERT INTO TEACHER_HAS_ROLE (email, id_role) VALUES ('b@b', 1);
INSERT INTO TEACHER_HAS_ROLE (email, id_role) VALUES ('c@c', 1);
INSERT INTO TEACHER_HAS_ROLE (email, id_role) VALUES ('tomas.libal123@gmail.com', 1);

INSERT INTO STUDENT (id_student, login, name_with_degrees) VALUES (STUDENT_SEQUENCE.nextval, 'xondra49','Bc. Lukáš Ondrák');
INSERT INTO STUDENT (id_student, login, name_with_degrees) VALUES (STUDENT_SEQUENCE.nextval, 'xnovak2b','Bc. Ondřej Novák');
INSERT INTO STUDENT (id_student, login, name_with_degrees) VALUES (STUDENT_SEQUENCE.nextval, 'xchoch07','Bc. Tomáš Chocholatý');
INSERT INTO STUDENT (id_student, login, name_with_degrees) VALUES (STUDENT_SEQUENCE.nextval, 'xlibal00','Bc. Tomáš Líbal');

