package cz.vutbr.fit.gja.models.exam;

import cz.vutbr.fit.gja.dto.ExamDto;

import java.util.List;

public interface ExamServiceDao {

    List<Exam> getAllExamsFromDatabase();

    Exam getExam(int id);

    Exam saveExamToDatabase(Exam exam);

    long deleteExam(int examId) throws IllegalAccessError;

    int setSpacingOfExam(String spacing);

    ExamDto getExamDto(Exam exam);
}
