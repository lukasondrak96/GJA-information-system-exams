package cz.vutbr.fit.gja.models.teacher;

public interface TeacherServiceDao {

    void saveTeacher(Teacher teacher);

    boolean isTeacherAlreadySaved(Teacher teacher);

    boolean isPasswordSame(Teacher teacher);

    Teacher getTeacher(String email);
}
