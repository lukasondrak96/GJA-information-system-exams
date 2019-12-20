package cz.vutbr.fit.gja.models.examRun;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRunRepository extends JpaRepository<ExamRun, Integer> {

    ExamRun findByIdExamRun(int id);

}
