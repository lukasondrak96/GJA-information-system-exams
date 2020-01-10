package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.examRun.ExamRun;

/**
 * This class represents exam that the student will take with additional information about exam run
 */
public class StudentExamPlaceDto extends StudentExamDto {
    /**
     * Exam run which student attends
     */
    private ExamRun examRun;

    public StudentExamPlaceDto(ExamRun examRun) {
        this.examRun = examRun;
    }

    public StudentExamPlaceDto(String subject, String examName, String academicYear, String roomNumber, String examDate, String startTime, String endTime, ExamRun examRun) {
        super(subject, examName, academicYear, roomNumber, examDate, startTime, endTime);
        this.examRun = examRun;
    }

    public ExamRun getExamRun() {
        return examRun;
    }

    public void setExamRun(ExamRun examRun) {
        this.examRun = examRun;
    }
}
