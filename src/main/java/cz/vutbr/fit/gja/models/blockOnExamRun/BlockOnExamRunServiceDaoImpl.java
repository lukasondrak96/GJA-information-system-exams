package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.models.block.Block;
import cz.vutbr.fit.gja.models.block.BlockServiceDaoImpl;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.room.Room;
import cz.vutbr.fit.gja.models.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class BlockOnExamRunServiceDaoImpl implements BlockOnExamRunServiceDao {

    @Autowired
    BlockServiceDaoImpl blockServiceDao;

    @Autowired
    BlockOnExamRunRepository blockOnExamRunRepository;

    @Override
    public void createAndSaveBlocksOnExamRun(ExamRun examRun, LinkedList<Student> students, int spacing) {
        Room room = examRun.getRoomReference();
        List<List<Block>> blocksInRoom = new ArrayList<>();

        //prepare list
        for (int i = 0; i < room.getNumberOfRows(); i++) {
            List<Block> blocksInRoomRow = new ArrayList<>();
            for (int j = 0; j < room.getNumberOfColumns(); j++) {
                blocksInRoomRow.add(new Block());
            }
            blocksInRoom.add(blocksInRoomRow);
        }

        for(Block block : blockServiceDao.getAllBlocks(room)) {
            blocksInRoom.get(room.getNumberOfRows() - block.getRowNumber()).set(block.getColumnNumber() - 1, block);
        }

        int seatCounter = 1;
        boolean goRight = true;
        for (int i = blocksInRoom.size() - 1; i >= 0; i--) {
            int spaceCounter = 0;
            if(goRight) {
                for (int j = 0; j < blocksInRoom.get(i).size(); j++) {
                    seatCounter = saveBlockOnExamRun(examRun, students, spacing, blocksInRoom, seatCounter, spaceCounter, i, j);
                    spaceCounter++;
                }
            } else {
                for (int j = blocksInRoom.get(i).size() - 1; j >= 0; j--) {
                    seatCounter = saveBlockOnExamRun(examRun, students, spacing, blocksInRoom, seatCounter, spaceCounter, i, j);
                    spaceCounter++;
                }
            }
            goRight = !goRight;
        }
    }

    private int saveBlockOnExamRun(ExamRun examRun, LinkedList<Student> students, int spacing, List<List<Block>> blocksInRoom, int seatCounter, int spaceCounter, int i, int j) {
        BlockOnExamRun blockOnExamRun = new BlockOnExamRun();
        Block block = blocksInRoom.get(i).get(j);
        if(block.isSeat()) {
            if(((spaceCounter % (spacing + 1)) == 0) && !students.isEmpty()) {
                blockOnExamRun = new BlockOnExamRun(String.valueOf(seatCounter), examRun, block, students.removeFirst());
                seatCounter++;
            } else {
                blockOnExamRun = new BlockOnExamRun("-", examRun, block, null);
            }
        } else {
            blockOnExamRun = new BlockOnExamRun("&nbsp;", examRun, block, null);
        }
        blockOnExamRunRepository.save(blockOnExamRun);
        return seatCounter;
    }
}
