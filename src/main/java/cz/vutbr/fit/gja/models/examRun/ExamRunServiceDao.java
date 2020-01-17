package cz.vutbr.fit.gja.models.examRun;

import cz.vutbr.fit.gja.models.exam.Exam;
import cz.vutbr.fit.gja.models.room.Room;

import java.util.List;

/**
 * This class encapsulates all methods for the ExamRun entity
 */
public interface ExamRunServiceDao {

    /**
     * Gets all exam runs from database
     * @return All exam runs in database
     */
    List<ExamRun> getAllExamRunsFromDatabase();

    /**
     * Gets exam run by given ID
     * @param id Exam run ID
     * @return Exam run
     */
    ExamRun getExamRun(int id);

    /**
     * Saves exam run to database
     * @param examRun Exam run to be saved
     */
    void saveExamRunToDatabase(ExamRun examRun);

    /**
     * Gets all exam runs which are related to given exam
     * @param exam Exam
     * @return All exam runs which are related to given exam
     */
    List<ExamRun> getAllExamRunsByExam(Exam exam);

    /**
     * Gets all exam runs which take place in given room
     * @param room Room
     * @return All exam runs which take place in given room
     */
    List<ExamRun> getAllExamRunsByRoomReference(Room room);

    /**
     * Return number of exam runs which are in time collision
     * @param room Room in which exam run take place
     * @param examDate Date of exam run
     * @param startTime Time when exam run starts
     * @param endTime Time when exam run ends
     * @return Number of exam runs which are in time collision
     */
    long getNumberOfExamRunsInCollision(Room room, String examDate, String startTime, String endTime);
}
