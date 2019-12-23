package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.exam.Exam;
import cz.vutbr.fit.gja.models.examRun.ExamRun;

import java.util.List;

public class ExamRunsDto {
    private int numberOfStudents;
    private List<ExamRun> examRuns;
    private Exam exam;

    public ExamRunsDto() {
    }

    public ExamRunsDto(int numberOfStudents, List<ExamRun> examRuns, Exam exam) {
        this.numberOfStudents = numberOfStudents;
        this.examRuns = examRuns;
        this.exam = exam;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public List<ExamRun> getExamRuns() {
        return examRuns;
    }

    public void setExamRuns(List<ExamRun> examRuns) {
        this.examRuns = examRuns;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
