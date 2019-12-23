package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.student.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockOnExamRunServiceDaoImpl implements BlockOnExamRunServiceDao {

    @Override
    public void createAndSaveBlocksOnExamRun(ExamRun examRun, List<Student> students) {

//        BlockOnExamRun blockOnExamRun = new BlockOnExamRun(examRun, );
//        for(examRun.getRoomReference())
    }
}
