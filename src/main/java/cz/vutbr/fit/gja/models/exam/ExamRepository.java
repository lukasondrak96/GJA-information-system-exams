package cz.vutbr.fit.gja.models.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class encapsulates all methods for the Exam entity that communicates with the database
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    /**
     * Gets exam by given ID
     * @param id Exam ID
     * @return Exam
     */
    Exam findByIdExam(int id);

    /**
     * Deletes exam identified by given ID
     * @param id Exam ID
     * @return Number of deleted exams
     */
    @Transactional
    Long deleteByIdExam(int id);

}
