package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.models.examRun.ExamRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class encapsulates all methods for the BlockOnExamRun entity that communicates with the database
 */
@Repository
public interface BlockOnExamRunRepository extends JpaRepository<BlockOnExamRun, Integer> {
    /**
     * Gets seating plan of given exam run
     * @param examRun Exam run
     * @return Seating plan of exam run
     */
    List<BlockOnExamRun> findAllByExamRunReference(ExamRun examRun);

    /**
     * Gets all exams in which the student is present
     * @param studentLogin Login of searched student
     * @return Exam runs in which the student is present
     */
    @Query("select er from BlockOnExamRun b join b.examRunReference er join b.studentReference s where s.login = ?1")
    List<ExamRun> getAllStudentExams(String studentLogin);
}
