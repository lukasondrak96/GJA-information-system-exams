package cz.vutbr.fit.gja.models.teacher;

/**
 * ServiceDao class for Teacher
 */
public interface TeacherServiceDao {

    /**
     * Save specific teacher to database
     *
     * @param teacher teacher to save
     */
    void saveTeacher(Teacher teacher);

    /**
     * Checks, if the teacher is saved in the database
     *
     * @param teacher specific teacher
     * @return true if teacher is already in database, false if not in database
     */
    boolean isTeacherAlreadySaved(Teacher teacher);

    /**
     * Checks, if password and repeated password of teacher are same
     *
     * @param teacher specific teacher
     * @return true if passwords are same, false if passwords are different
     */
    boolean isPasswordSame(Teacher teacher);

    /**
     * Gets specific teacher from database
     *
     * @param email email of teacher
     * @return specific teacher
     */
    Teacher getTeacher(String email);
}
