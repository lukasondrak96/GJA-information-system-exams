package cz.vutbr.fit.gja.models.exam;

import cz.vutbr.fit.gja.dto.ExamDto;
import cz.vutbr.fit.gja.models.room.Room;

import java.util.List;

/**
 * This class encapsulates all methods for the Exam entity
 */
public interface ExamServiceDao {

    /**
     * Gets all exams from database
     * @return All exams in database
     */
    List<Exam> getAllExamsFromDatabase();

    /**
     * Gets exam by given ID
     * @param id Exam ID
     * @return Exam
     */
    Exam getExam(int id);

    /**
     * Saves exam to database
     * @param exam Exam to be saved
     * @return Saved exam
     */
    Exam saveExamToDatabase(Exam exam);

    /**
     * Deletes exam identified by given ID
     * @param examId Exam ID
     * @return Number of deleted exams
     * @throws IllegalAccessError Error while deleting exam
     */
    long deleteExam(int examId) throws IllegalAccessError;

    /**
     * Sets spacing between students on exam
     * @param spacing Spacing as string (no_space|one_space|two_space)
     * @return Spacing as number
     */
    int setSpacingOfExam(String spacing);

    /**
     * Gets all exam runs which are related to given exam and wraps it together
     * with given exam to ExamDto object
     * @param exam Exam
     * @return Object with exam and hers exam runs
     */
    ExamDto getExamDto(Exam exam);

    /**
     * Checks if there is any exam in given room
     * @param room Room
     * @return true - there is some exam in the given room, false - there is not any exam in the given room
     */
    boolean checkIfExamRunIsInRoom(Room room);
}
