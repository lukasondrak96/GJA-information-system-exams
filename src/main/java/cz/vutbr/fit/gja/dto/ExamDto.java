package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.exam.Exam;

import java.util.List;

/**
 * This class represents one specific exam
 */
public class ExamDto {
    /**
     * List of exam runs of exam
     */
    private List<ExamRunForSeatingDto> examRuns;

    /**
     * Exam
     */
    private Exam exam;

    public ExamDto() {
    }

    public ExamDto(List<ExamRunForSeatingDto> examRuns, Exam exam) {
        this.examRuns = examRuns;
        this.exam = exam;
    }

    public List<ExamRunForSeatingDto> getExamRuns() {
        return examRuns;
    }

    public void setExamRuns(List<ExamRunForSeatingDto> examRuns) {
        this.examRuns = examRuns;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
