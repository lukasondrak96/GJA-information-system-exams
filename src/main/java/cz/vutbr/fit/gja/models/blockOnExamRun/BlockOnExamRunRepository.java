package cz.vutbr.fit.gja.models.blockOnExamRun;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BlockOnExamRunRepository extends JpaRepository<BlockOnExamRun, Integer> {

}
