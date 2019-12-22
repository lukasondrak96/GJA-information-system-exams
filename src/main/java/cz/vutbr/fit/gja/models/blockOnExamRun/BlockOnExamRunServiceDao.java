package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.models.examRun.ExamRun;

import java.util.List;

public interface BlockOnExamRunServiceDao {
    void createAndSaveBlocksOnExamRun(ExamRun examRun, List<String> studentsLogins);
}
