package cz.vutbr.fit.gja.dto;

/**
 * This class represents exam that the student will take with additional information about room and exam run
 */
public class StudentExamPlaceDto extends StudentExamDto {
    /**
     * Room in which exam takes place
     */
    private long roomId;

    /**
     * Exam run which student attends
     */
    private long examRunId;

    public StudentExamPlaceDto(long roomId, long examRunId) {
        this.roomId = roomId;
        this.examRunId = examRunId;
    }

    public StudentExamPlaceDto(String subject, String examName, String academicYear, String roomNumber, String examDate, String startTime, String endTime, long roomId, long examRunId) {
        super(subject, examName, academicYear, roomNumber, examDate, startTime, endTime);
        this.roomId = roomId;
        this.examRunId = examRunId;
    }
}
