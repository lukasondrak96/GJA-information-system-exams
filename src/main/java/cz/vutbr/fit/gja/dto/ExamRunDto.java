package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.exam.Exam;
import cz.vutbr.fit.gja.models.examRun.ExamRun;

/**
 * This class is wrapping information from second part of new run form
 */
public class ExamRunDto {
    /**
     * Number of students on exam
     */
    private int numberOfStudents;

    /**
     * Exam Run which will be saved to database
     */
    private ExamRun examRun;

    /**
     * Exam which will be saved to database
     */
    private Exam exam;

    public ExamRunDto() {
    }

    public ExamRunDto(int numberOfStudents, ExamRun examRun, Exam exam) {
        this.numberOfStudents = numberOfStudents;
        this.examRun = examRun;
        this.exam = exam;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public ExamRun getExamRun() {
        return examRun;
    }

    public void setExamRun(ExamRun examRun) {
        this.examRun = examRun;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
