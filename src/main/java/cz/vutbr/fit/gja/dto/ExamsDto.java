package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.exam.Exam;
import cz.vutbr.fit.gja.models.examRun.ExamRun;

import java.util.List;

/**
 * This class is wrapping information about exam to show on frontend
 */
public class ExamsDto {
    /**
     * Exam run
     */
    private Exam exam;

    /**
     * List of exams run
     */
    private List<ExamRun> list;

    public ExamsDto(Exam exam, List<ExamRun> list) {
        this.exam = exam;
        this.list = list;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public List<ExamRun> getList() {
        return list;
    }

    public void setList(List<ExamRun> list) {
        this.list = list;
    }
}
