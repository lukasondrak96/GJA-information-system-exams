package cz.vutbr.fit.gja.models.exam;

import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.teacher.Teacher;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "exam")
public class Exam {

    @Id
    @NotNull(message="Id zkoušky je povinné")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_exam")
    private int idExam;

    @Column(name = "exam_name")
    private String examName;

    @Column(name = "academic_year")
    private String academicYear;

    @Column(name = "subject")
    private String subject;

    @Column(name = "spacing_between_students")
    @Range(min=0, max=2, message="Mezery mezi studenty mohou být v rozmezí 0 až 2")
    private int spacingBetweenStudents;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_exam_creator", referencedColumnName = "email")
    private Teacher idExamCreator;

    @OneToMany(mappedBy = "idExam")
    private Set<ExamRun> examRuns;

    public Exam() {
    }

    public Exam(String examName, String academicYear, String subject, @Range(min = 0, max = 2, message = "Mezery mezi studenty mohou být v rozmezí 0 až 2") int spacingBetweenStudents, Teacher idExamCreator) {
        this.examName = examName;
        this.academicYear = academicYear;
        this.subject = subject;
        this.spacingBetweenStudents = spacingBetweenStudents;
        this.idExamCreator = idExamCreator;
    }

    public int getIdExam() {
        return idExam;
    }

    public void setIdExam(int idExam) {
        this.idExam = idExam;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSpacingBetweenStudents() {
        return spacingBetweenStudents;
    }

    public void setSpacingBetweenStudents(int spacingBetweenStudents) {
        this.spacingBetweenStudents = spacingBetweenStudents;
    }

    public Teacher getIdExamCreator() {
        return idExamCreator;
    }

    public void setIdExamCreator(Teacher idExamCreator) {
        this.idExamCreator = idExamCreator;
    }


}
