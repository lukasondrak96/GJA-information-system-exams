package cz.vutbr.fit.gja.models.student;

import java.util.List;

public interface StudentServiceDao {

    List<Student> getAllStudentsFromDatabase();

    Student getStudentByLogin(String login);

    Student saveStudentToDatabase(Student student);

    long deleteStudent(String login) throws IllegalAccessError;
}
