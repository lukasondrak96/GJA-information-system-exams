package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.dto.StudentExamDto;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlockOnExamRunRepository extends JpaRepository<BlockOnExamRun, Integer> {
    List<BlockOnExamRun> findAllByExamRunReference(ExamRun examRun);

    @Query(value = "select E.SUBJECT, E.EXAM_NAME, E.ACADEMIC_YEAR, R.ROOM_NUMBER, ER.EXAM_DATE, ER.START_TIME, ER.END_TIME from BLOCK_ON_EXAM_RUN B JOIN STUDENT S on B.STUDENT_REFERENCE = S.ID_STUDENT JOIN EXAM_RUN ER on B.EXAM_RUN_REFERENCE = ER.ID_EXAM_RUN JOIN EXAM E on ER.EXAM_REFERENCE = E.ID_EXAM JOIN ROOM R on ER.ROOM_REFERENCE = R.ID_ROOM where S.LOGIN=?1", nativeQuery = true)
    List<StudentExamDto> getAllStudentExams(String studentLogin);
}
