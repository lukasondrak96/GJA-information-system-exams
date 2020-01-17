package cz.vutbr.fit.gja.models.teacher;

/**
 * ServiceDao class for Teacher
 */
public interface TeacherServiceDao {

    /**
     * Save specific teacher to database
     * @param teacher Teacher to save
     */
    void saveTeacher(Teacher teacher);

    /**
     * Checks, if the teacher is saved in the database
     * @param teacher Inspected teacher
     * @return true if teacher is already in database, false if not in database
     */
    boolean isTeacherAlreadySaved(Teacher teacher);

    /**
     * Checks, if password and repeated password of teacher are same
     * @param teacher Teacher which passwords will be compared
     * @return true if passwords are same, false if passwords are different
     */
    boolean isPasswordSame(Teacher teacher);

    /**
     * Gets specific teacher from database
     * @param email Teacher email
     * @return Teacher with given email
     */
    Teacher getTeacher(String email);
}
