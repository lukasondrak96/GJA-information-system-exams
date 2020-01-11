package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.student.Student;

import java.util.LinkedList;
import java.util.List;

public interface BlockOnExamRunServiceDao {
    /**
     * Creates seating plan on exam run
     * @param examRun Exam run where students should participate
     * @param students Students to be seated
     * @param spacing Spacing between students
     * @return Number of seated students
     */
    int createAndSaveBlocksOnExamRun(ExamRun examRun, LinkedList<Student> students, int spacing);

    /**
     * Gets seating plan of an exam run
     * @param examRun Exam run
     * @return Seating plan of an exam run
     */
    List<List<BlockOnExamRun>> getSeating(ExamRun examRun);

    /**
     * Gets all exams in which the student is present
     * @param login Login of searched student
     * @return Exam runs in which the student is present
     */
    List<ExamRun> getAllStudentExams(String login);
}
