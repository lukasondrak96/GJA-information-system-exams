package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.student.Student;

import java.util.List;

public interface BlockOnExamRunServiceDao {
    void createAndSaveBlocksOnExamRun(ExamRun examRun, List<Student> students);
}
