package cz.vutbr.fit.gja.models.examRun;

import cz.vutbr.fit.gja.models.exam.Exam;
import cz.vutbr.fit.gja.models.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class encapsulates all methods for the ExamRun entity that communicates with the database
 */
@Repository
public interface ExamRunRepository extends JpaRepository<ExamRun, Integer> {

    /**
     * Gets exam run by given ID
     * @param id Exam run ID
     * @return ExamRun
     */
    ExamRun findByIdExamRun(int id);

    /**
     * Gets all exam runs of given exam
     * @param exam Exam
     * @return All exam runs of the exam
     */
    List<ExamRun> findAllByExamReference(Exam exam);

    /**
     * Gets all exam runs which take place in given room
     * @param room Room
     * @return All exam runs which take place in given room
     */
    List<ExamRun> findAllByRoomReference(Room room);

    /**
     * Return all exam runs which are in time collision
     * @param room Room in which exam run take place
     * @param examDate Date of exam run
     * @param startTime Time when exam run starts
     * @param endTime Time when exam run ends
     * @return Exam runs which are in time collision
     */
    @Query("select er from ExamRun er where er.roomReference = ?1 and er.examDate = ?2 and (" +
            "(er.startTime between ?3 and ?4 or er.endTime between ?3 and ?4) or (er.startTime < ?3 and er.endTime > ?4)" +
            ")")
    List<ExamRun> findExamRunsInCollision(Room room, String examDate, String startTime, String endTime);
}
