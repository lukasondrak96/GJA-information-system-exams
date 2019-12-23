package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.models.examRun.ExamRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlockOnExamRunRepository extends JpaRepository<BlockOnExamRun, Integer> {
    List<BlockOnExamRun> findAllByExamRunReference(ExamRun examRun);
}
