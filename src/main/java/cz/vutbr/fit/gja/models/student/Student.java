package cz.vutbr.fit.gja.models.student;

import cz.vutbr.fit.gja.models.blockOnExamRun.BlockOnExamRun;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @SequenceGenerator(name = "StudentIdGenerator", sequenceName = "STUDENT_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "StudentIdGenerator")
    @Column(name = "id_student")
    private int idStudent;

    @NotNull(message="Login je u studenta povinný")
    @Column(name = "login")
    private String login;

    @Column(name = "name_with_degrees")
    private String nameWithDegrees;

//    @OneToMany(mappedBy = "student")
//    private Set<BlockOnExamRun> runsOfStudent;

    public Student() {
    }

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
}
