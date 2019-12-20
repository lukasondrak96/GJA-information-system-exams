package cz.vutbr.fit.gja.models.examRun;

import cz.vutbr.fit.gja.models.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExamRunRepository extends JpaRepository<ExamRun, Integer> {

    ExamRun findByIdExamRun(int id);

    List<ExamRun> findAllByExamReference(Exam exam);
}
