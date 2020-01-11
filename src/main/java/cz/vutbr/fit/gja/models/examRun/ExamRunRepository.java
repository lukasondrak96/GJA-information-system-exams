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

    ExamRun findByIdExamRun(int id);

    List<ExamRun> findAllByExamReference(Exam exam);

    List<ExamRun> findAllByRoomReference(Room room);

    @Query("select er from ExamRun er where er.roomReference = ?1 and er.examDate = ?2 and (" +
            "(er.startTime between ?3 and ?4 or er.endTime between ?3 and ?4) or (er.startTime < ?3 and er.endTime > ?4)" +
            ")")
    List<ExamRun> findExamRunsInCollision(Room room, String examDate, String startTime, String endTime);
}
