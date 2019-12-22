package cz.vutbr.fit.gja.models.exam;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceDaoImpl implements ExamServiceDao {

    @Autowired
    ExamRepository examRepository;

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    @Override
    public List<Exam> getAllExamsFromDatabase() {
        return examRepository.findAll();
    }

    @Override
    public Exam getExam(int id) {
        return examRepository.findByIdExam(id);
    }

    @Override
    public Exam saveExamToDatabase(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public long deleteExam(int examId) throws IllegalAccessError {
        //todo
        throw new NotYetImplementedException();
    }

    @Override
    public void setSpacingOfExam(Exam exam, String spacing) {
        int spacesBetweenStudents;
        switch (spacing) {
            default:
            case "no_space":
                spacesBetweenStudents = 0;
                break;
            case "one_space":
                spacesBetweenStudents = 1;
                break;
            case "two_space":
                spacesBetweenStudents = 2;
                break;
        }
        exam.setSpacingBetweenStudents(spacesBetweenStudents);
    }

}
