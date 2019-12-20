package cz.vutbr.fit.gja.models.examRun;

import cz.vutbr.fit.gja.models.exam.Exam;

import java.util.List;

public interface ExamRunServiceDao {

    List<ExamRun> getAllExamRunsFromDatabase();

    ExamRun getExamRun(int id);

    void saveExamRunToDatabase(ExamRun examRun);

    long deleteExamRun(int examRunId) throws IllegalAccessError;

    List<ExamRun> getAllExamRunsByExam(Exam exam);
}
