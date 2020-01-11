package cz.vutbr.fit.gja.models.exam;

import cz.vutbr.fit.gja.models.teacher.Teacher;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This class represents the entity of Exam
 */
@Entity
@Table(name = "exam")
public class Exam {

    /**
     * Exam ID
     */
    @Id
    @NotNull(message="Id zkoušky je povinné")
    @SequenceGenerator(name = "ExamIdGenerator", sequenceName = "EXAM_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ExamIdGenerator")
    @Column(name = "id_exam")
    private int idExam;

    /**
     * Exam name
     */
    @NotNull(message="Jméno je povinné")
    @Column(name = "exam_name")
    private String examName;

    /**
     * Academic year in which exam takes place
     */
    @NotNull(message="Akademický rok je povinný")
    @Column(name = "academic_year")
    private String academicYear;

    /**
     * Subject from which the exam is taken
     */
    @NotNull(message="Předmět je povinný")
    @Column(name = "subject")
    private String subject;

    /**
     * Number of free seats between students on exam
     */
    @Column(name = "spacing_between_students")
    @Range(min=0, max=2, message="Mezery mezi studenty mohou být v rozmezí 0 až 2")
    private int spacingBetweenStudents;

    /**
     * Reference to the exam creator (teacher)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "email_exam_creator", referencedColumnName = "email")
    private Teacher examCreator;

    /**
     * Creates a new exam
     */
    public Exam() {
    }

    /**
     * Creates a new exam
     * @param examName Exam name
     * @param academicYear Academic year in which exam takes place
     * @param subject Subject from which the exam is taken
     * @param spacingBetweenStudents Number of free seats between students on exam
     * @param examCreator Reference to the exam creator (teacher)
     */
    public Exam(String examName, String academicYear, String subject, @Range(min = 0, max = 2, message = "Mezery mezi studenty mohou být v rozmezí 0 až 2") int spacingBetweenStudents, Teacher examCreator) {
        this.examName = examName;
        this.academicYear = academicYear;
        this.subject = subject;
        this.spacingBetweenStudents = spacingBetweenStudents;
        this.examCreator = examCreator;
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

    public Teacher getExamCreator() {
        return examCreator;
    }

    public void setExamCreator(Teacher examCreator) {
        this.examCreator = examCreator;
    }

}
