package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.student.Student;

import java.util.LinkedList;
import java.util.List;

public interface BlockOnExamRunServiceDao {
    LinkedList<Student> createAndSaveBlocksOnExamRun(ExamRun examRun, LinkedList<Student> students, int spacing);

    List<List<BlockOnExamRun>> getSeating(ExamRun examRun);
}
