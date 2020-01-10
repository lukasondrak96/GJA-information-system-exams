package cz.vutbr.fit.gja.dto;

/**
 * This class represents exam that the student will take
 */
public class StudentExamDto {
    /**
     * The subject of the exam
     */
    private String subject;

    /**
     * The name of the exam
     */
    private String examName;

    /**
     * Academic year in which the exam takes place
     */
    private String academicYear;

    /**
     * Room number in which the exam takes place
     */
    private String roomNumber;

    /**
     * The date the exam takes place
     */
    private String examDate;

    /**
     * Exam start time
     */
    private String startTime;

    /**
     * Exam end time
     */
    private String endTime;

    public StudentExamDto() {
    }

    public StudentExamDto(String subject, String examName, String academicYear, String roomNumber, String examDate, String startTime, String endTime) {
        this.subject = subject;
        this.examName = examName;
        this.academicYear = academicYear;
        this.roomNumber = roomNumber;
        this.examDate = examDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
