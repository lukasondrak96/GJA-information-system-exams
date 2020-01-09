package cz.vutbr.fit.gja.models.student;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This class represents entity of Students.
 */
@Entity
@Table(name = "student")
public class Student implements Comparable<Student> {

    /**
     * ID of student
     */
    @Id
    @SequenceGenerator(name = "StudentIdGenerator", sequenceName = "STUDENT_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "StudentIdGenerator")
    @Column(name = "id_student")
    private int idStudent;

    /**
     * Login of student
     */
    @NotNull(message = "Login je u studenta povinný")
    @Column(name = "login")
    private String login;

    /**
     * Name of Student (inc. degrees)
     */
    @Column(name = "name_with_degrees")
    private String nameWithDegrees;

    public Student() {
    }

    /**
     * Creates new Student
     *
     * @param login           login of student
     * @param nameWithDegrees name of student
     */
    public Student(@NotNull(message = "Login je u studenta povinný") String login, String nameWithDegrees) {
        this.login = login;
        this.nameWithDegrees = nameWithDegrees;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNameWithDegrees() {
        return nameWithDegrees;
    }

    public void setNameWithDegrees(String nameWithDegrees) {
        this.nameWithDegrees = nameWithDegrees;
    }

    /**
     * Compares logins of students
     *
     * @param s student
     * @return 0 if any is null, otherwise uses classic compareTo()
     */
    @Override
    public int compareTo(Student s) {
        if (getLogin() == null || s.getLogin() == null) {
            return 0;
        }
        return getLogin().compareTo(s.getLogin());
    }
}
