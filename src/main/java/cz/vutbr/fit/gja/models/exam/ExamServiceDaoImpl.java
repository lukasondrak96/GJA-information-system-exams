package cz.vutbr.fit.gja.models.exam;

import cz.vutbr.fit.gja.dto.BlocksDto;
import cz.vutbr.fit.gja.dto.ExamDto;
import cz.vutbr.fit.gja.dto.ExamRunForSeating;
import cz.vutbr.fit.gja.models.block.BlockServiceDaoImpl;
import cz.vutbr.fit.gja.models.blockOnExamRun.BlockOnExamRun;
import cz.vutbr.fit.gja.models.blockOnExamRun.BlockOnExamRunServiceDaoImpl;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.examRun.ExamRunRepository;
import cz.vutbr.fit.gja.models.examRun.ExamRunServiceDaoImpl;
import cz.vutbr.fit.gja.models.room.Room;
import cz.vutbr.fit.gja.models.room.RoomServiceDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamServiceDaoImpl implements ExamServiceDao {

    @Autowired
    ExamRepository examRepository;

    @Autowired
    ExamRunRepository examRunRepository;

    @Autowired
    ExamRunServiceDaoImpl examRunServiceDao;

    @Autowired
    BlockServiceDaoImpl blockServiceDao;

    @Autowired
    RoomServiceDaoImpl roomServiceDao;

    @Autowired
    BlockOnExamRunServiceDaoImpl blockOnExamRunServiceDao;

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
        Exam exam = examRepository.findByIdExam(examId);
        if(exam == null) {
            return 0;
        }
        return examRepository.deleteByIdExam(examId);
    }

    @Override
    public int setSpacingOfExam(String spacing) {
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
        return spacesBetweenStudents;
    }

    @Override
    public ExamDto getExamDto(Exam exam) {
        ExamDto examDto = new ExamDto();
        examDto.setExam(exam);

        List<ExamRun> examRuns = examRunServiceDao.getAllExamRunsByExam(exam);
        List<ExamRunForSeating> examRunsForSeating = new ArrayList<>();
        for (ExamRun examRun : examRuns) {
            Room room = roomServiceDao.getRoomByRoomNumber(examRun.getRoomReference().getRoomNumber());
            BlocksDto blocks = blockServiceDao.getAllBlocksOfRoomAsDto(room);
            List<List<BlockOnExamRun>> seating = blockOnExamRunServiceDao.getSeating(examRun);

            examRunsForSeating.add(new ExamRunForSeating(examRun.getExamDate(), examRun.getStartTime(), examRun.getEndTime(), blocks, seating));
        }
        examDto.setExamRuns(examRunsForSeating);

        return examDto;
    }

    @Override
    public boolean checkIfExamRunIsInRoom(Room room) {
        List<ExamRun> examRunsOfRoom = examRunRepository.findAllByRoomReference(room);
        return !examRunsOfRoom.isEmpty();
    }
}
