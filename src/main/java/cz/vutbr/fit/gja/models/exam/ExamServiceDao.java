package cz.vutbr.fit.gja.models.exam;

import java.util.List;

public interface ExamServiceDao {

    List<Exam> getAllExamsFromDatabase();

    Exam getExam(int id);

    void saveExamToDatabase(Exam exam);

    long deleteExam(int examId) throws IllegalAccessError;
}
