package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.exam.Exam;
import cz.vutbr.fit.gja.models.examRun.ExamRun;

import java.util.List;

public class ExamRunsDto {
    private List<String> studentsLogins;
    private List<ExamRun> examRuns;
    private Exam exam;

    public ExamRunsDto() {
    }

    public ExamRunsDto(List<String> studentsLogins, List<ExamRun> examRuns, Exam exam) {
        this.studentsLogins = studentsLogins;
        this.examRuns = examRuns;
        this.exam = exam;
    }

    public List<String> getStudentsLogins() {
        return studentsLogins;
    }

    public void setStudentsLogins(List<String> studentsLogins) {
        this.studentsLogins = studentsLogins;
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
