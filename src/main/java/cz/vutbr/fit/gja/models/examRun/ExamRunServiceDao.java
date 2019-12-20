package cz.vutbr.fit.gja.models.examRun;

import java.util.List;

public interface ExamRunServiceDao {

    List<ExamRun> getAllExamRunsFromDatabase();

    ExamRun getExamRun(int id);

    void saveExamRunToDatabase(ExamRun examRun);

    long deleteExamRun(int examRunId) throws IllegalAccessError;
}
