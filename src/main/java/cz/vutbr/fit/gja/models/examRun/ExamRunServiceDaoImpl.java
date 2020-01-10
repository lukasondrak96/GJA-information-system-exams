package cz.vutbr.fit.gja.models.examRun;

import cz.vutbr.fit.gja.models.exam.Exam;
import cz.vutbr.fit.gja.models.room.Room;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamRunServiceDaoImpl implements ExamRunServiceDao {

    @Autowired
    ExamRunRepository examRunRepository;

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    @Override
    public List<ExamRun> getAllExamRunsFromDatabase() {
        return examRunRepository.findAll();
    }

    @Override
    public ExamRun getExamRun(int id) {
        return examRunRepository.findByIdExamRun(id);
    }

    @Override
    public void saveExamRunToDatabase(ExamRun examRun) {
        examRunRepository.save(examRun);
    }

    @Override
    public long deleteExamRun(int examRunId) throws IllegalAccessError {
        //todo
        throw new NotYetImplementedException();
    }

    @Override
    public List<ExamRun> getAllExamRunsByExam(Exam exam) {
        return examRunRepository.findAllByExamReference(exam);
    }

    @Override
    public List<ExamRun> getAllExamRunsByRoomReference(Room room) {
        return examRunRepository.findAllByRoomReference(room);
    }

    @Override
    public long getNumberOfExamRunsInCollision(Room room, String examDate, String startTime, String endTime) {
        return examRunRepository.findExamRunsInCollision(room, examDate, startTime, endTime).size();
    }

}
