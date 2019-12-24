package cz.vutbr.fit.gja.models.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ExamRepository extends JpaRepository<Exam, Integer> {

    Exam findByIdExam(int id);

    @Transactional
    Long deleteByIdExam(int id);

}
