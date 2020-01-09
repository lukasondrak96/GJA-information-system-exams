package cz.vutbr.fit.gja.models.student;

import java.util.List;

/**
 * ServiceDao class for Student
 */
public interface StudentServiceDao {

    /**
     * Gets all students from database
     *
     * @return list of students from database
     */
    List<Student> getAllStudentsFromDatabase();

    /**
     * Gets specific student from database based on login
     *
     * @param login login of student
     * @return Student from database
     */
    Student getStudentByLogin(String login);

    /**
     * Saves specific student to database
     *
     * @param student student
     * @return specific Student
     */
    Student saveStudentToDatabase(Student student);

}
