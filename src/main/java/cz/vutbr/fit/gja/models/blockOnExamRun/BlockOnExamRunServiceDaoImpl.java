package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.models.examRun.ExamRun;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockOnExamRunServiceDaoImpl implements BlockOnExamRunServiceDao {

    @Override
    public void createAndSaveBlocksOnExamRun(ExamRun examRun, List<String> logins) {
        BlockOnExamRun blockOnExamRun = new BlockOnExamRun();
//        for(examRun.getRoomReference())
    }
}
