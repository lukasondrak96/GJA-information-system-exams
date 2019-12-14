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

INSERT INTO STUDENT (login, name_with_degrees) VALUES ('xondra49','Prokrastinator Student');
INSERT INTO STUDENT (login, name_with_degrees) VALUES ('xnovak2b','Pan bc. Novak');
INSERT INTO STUDENT (login, name_with_degrees) VALUES ('xchoch07','Chochot Cokot');

INSERT INTO ROOM (room_number, number_of_columns, number_of_rows, id_room_creator) VALUES ('E104', '15', '10', 'a@a');
INSERT INTO ROOM (room_number, number_of_columns, number_of_rows, id_room_creator) VALUES ('D105', '10', '10', 'a@a');
INSERT INTO ROOM (room_number, number_of_columns, number_of_rows, id_room_creator) VALUES ('E112', '20', '20', 'b@b');

 -- boolean is stored as 1/0
INSERT INTO BLOCK (id_block, is_seat, column_number, row_number, room_number_containing_block) VALUES (1, 1, 1, 1, 'E104');
INSERT INTO BLOCK (id_block, is_seat, column_number, row_number, room_number_containing_block) VALUES (2, 1, 2, 2, 'D105');
INSERT INTO BLOCK (id_block, is_seat, column_number, row_number, room_number_containing_block) VALUES (3, 1, 3, 2, 'D105');
INSERT INTO BLOCK (id_block, is_seat, column_number, row_number, room_number_containing_block) VALUES (4, 1, 4, 2, 'D105');
INSERT INTO BLOCK (id_block, is_seat, column_number, row_number, room_number_containing_block) VALUES (5, 0, 1, 4, 'E112');
INSERT INTO BLOCK (id_block, is_seat, column_number, row_number, room_number_containing_block) VALUES (6, 0, 1, 5, 'E112');

INSERT INTO EXAM (id_exam, academic_year, exam_name, spacing_between_students, subject, id_exam_creator) VALUES (1, '2014/2015', 'IZU-semestralka', 2, 'IZU', 'a@a');
INSERT INTO EXAM (id_exam, academic_year, exam_name, spacing_between_students, subject, id_exam_creator) VALUES (2, '2019/2020', 'TIN-semestralka', 1, 'TIN', 'a@a');
INSERT INTO EXAM (id_exam, academic_year, exam_name, spacing_between_students, subject, id_exam_creator) VALUES (3, '2014/2015', 'IMA-semestralka', 0, 'IMA', 'b@b');

INSERT INTO EXAM_RUN (id_exam_run, end_time, start_time, id_exam, room_number) VALUES (1, '18:00', '15:00', 1, 'D105');
INSERT INTO EXAM_RUN (id_exam_run, end_time, start_time, id_exam, room_number) VALUES (2, '18:00', '15:00', 1, 'D105');
INSERT INTO EXAM_RUN (id_exam_run, end_time, start_time, id_exam, room_number) VALUES (3, '20:00', '18:00', 1, 'D105');

INSERT INTO BLOCK_ON_EXAM_RUN (id_block_on_exam_run, id_block, id_exam_run, login_of_student_in_block) VALUES (1, 1, 1, 'xondra49');
INSERT INTO BLOCK_ON_EXAM_RUN (id_block_on_exam_run, id_block, id_exam_run, login_of_student_in_block) VALUES (2, 2, 1, 'xnovak2b');
INSERT INTO BLOCK_ON_EXAM_RUN (id_block_on_exam_run, id_block, id_exam_run, login_of_student_in_block) VALUES (3, 1, 2, 'xchoch07');
