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
    public int createAndSaveBlocksOnExamRun(ExamRun examRun, LinkedList<Student> students, int spacing) {
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

        for (Block block : blockServiceDao.getAllBlocks(room)) {
            blocksInRoom.get(room.getNumberOfRows() - block.getRowNumber()).set(block.getColumnNumber() - 1, block);
        }

        int seatCounter = 0;
        boolean goRight = true;
        boolean firstFlag = true;
        OccupiedSeats occupiedSeats = new OccupiedSeats(false, false, seatCounter, false, new ArrayList<>());
        for (int i = blocksInRoom.size() - 1; i >= 0; i--) {
            ArrayList<Block> blocksInRow = (ArrayList<Block>) blocksInRoom.get(i);
            occupiedSeats = saveBlocksInRowOnExamRun(examRun, students, spacing, blocksInRow, seatCounter, i, goRight, firstFlag, occupiedSeats.patternForSeat, occupiedSeats);
            seatCounter = occupiedSeats.seatCounter;
            if(firstFlag){
                firstFlag = false;
            }
            goRight = !goRight;
        }
        return seatCounter;
    }

    @Override
    public List<List<BlockOnExamRun>> getSeating(ExamRun examRun) {
        Room room = examRun.getRoomReference();
        List<List<BlockOnExamRun>> seating = new ArrayList<>();

        //prepare list
        for (int i = 0; i < room.getNumberOfRows(); i++) {
            List<BlockOnExamRun> seatingRow = new ArrayList<>();
            for (int j = 0; j < room.getNumberOfColumns(); j++) {
                seatingRow.add(new BlockOnExamRun());
            }
            seating.add(seatingRow);
        }

        for (BlockOnExamRun block : blockOnExamRunRepository.findAllByExamRunReference(examRun)) {
            seating.get(room.getNumberOfRows() - block.getBlockReference().getRowNumber()).set(block.getBlockReference().getColumnNumber() - 1, block);
        }
        return seating;
    }

    @Override
    public List<ExamRun> getAllStudentExams(String login) {
        return blockOnExamRunRepository.getAllStudentExams(login);
    }

    private OccupiedSeats saveBlocksInRowOnExamRun(ExamRun examRun, LinkedList<Student> students, int spacing, List<Block> blocksInRow, int seatCounter, int i, boolean goRight, boolean firstFlag, ArrayList<Integer> patternForSeat, OccupiedSeats oldOccupiedSeats) {
        OccupiedSeats occupiedSeats = new OccupiedSeats(false, false, seatCounter, false, new ArrayList<>());
        occupiedSeats.patternForSeat = oldOccupiedSeats.patternForSeat;
        if(firstFlag){
            occupiedSeats.firstFlag = true;
        }
        if (goRight) {
            for (int j = 0; j < blocksInRow.size(); j++) {
                occupiedSeats = saveBlock(examRun, students, spacing, blocksInRow, occupiedSeats, i, j);
            }
        } else {
            for (int j = blocksInRow.size() - 1; j >= 0; j--) {
                occupiedSeats = saveBlock(examRun, students, spacing, blocksInRow, occupiedSeats, i, j);
            }
        }
        return occupiedSeats;
    }

    private OccupiedSeats saveBlock(ExamRun examRun, LinkedList<Student> students, int spacing, List<Block> blocksInRow, OccupiedSeats occupiedSeats, int i, int j) {
        BlockOnExamRun blockOnExamRun;
        Block block = blocksInRow.get(j);
        boolean lastBlockIsOccupied = occupiedSeats.lastOccupied;
        boolean secondLastBlockIsOccupied = occupiedSeats.secondLastOccupied;
        boolean firstFlag = occupiedSeats.firstFlag;
        ArrayList<Integer> patternForSeat = occupiedSeats.patternForSeat;

        if(firstFlag) {
            if (block.isSeat()) {
                if (((lastBlockIsOccupied && spacing == 1) || ((secondLastBlockIsOccupied || lastBlockIsOccupied) && spacing == 2)) || students.isEmpty()) {
                    blockOnExamRun = new BlockOnExamRun("-", examRun, block, null);
                    secondLastBlockIsOccupied = lastBlockIsOccupied;
                    lastBlockIsOccupied = false;

                } else {
                    secondLastBlockIsOccupied = lastBlockIsOccupied;
                    lastBlockIsOccupied = true;
                    occupiedSeats.seatCounter++;
                    blockOnExamRun = new BlockOnExamRun(String.valueOf(occupiedSeats.seatCounter), examRun, block, students.removeFirst());
                    if (firstFlag) {
                        patternForSeat.add(j);
                    }
                }
            } else {
                blockOnExamRun = new BlockOnExamRun(" ", examRun, block, null);
                secondLastBlockIsOccupied = lastBlockIsOccupied;
                lastBlockIsOccupied = false;
            }
        }else{
            if (block.isSeat()) {
                if (patternForSeat.contains(j) && !students.isEmpty()) {
                    occupiedSeats.seatCounter++;
                    blockOnExamRun = new BlockOnExamRun(String.valueOf(occupiedSeats.seatCounter), examRun, block, students.removeFirst());
                }else{
                    blockOnExamRun = new BlockOnExamRun("-", examRun, block, null);
                }
            }else{
                blockOnExamRun = new BlockOnExamRun(" ", examRun, block, null);
                secondLastBlockIsOccupied = lastBlockIsOccupied;
                lastBlockIsOccupied = false;
            }
        }
        blockOnExamRunRepository.save(blockOnExamRun);
        return new OccupiedSeats(lastBlockIsOccupied, secondLastBlockIsOccupied, occupiedSeats.seatCounter, firstFlag, patternForSeat);
    }

    private class OccupiedSeats {
        boolean lastOccupied;
        boolean secondLastOccupied;
        int seatCounter;
        boolean firstFlag;
        ArrayList<Integer> patternForSeat;

        public OccupiedSeats(boolean lastOccupied, boolean secondLastOccupied, int seatCounter, boolean firstFlag, ArrayList<Integer> patternForSeat) {
            this.lastOccupied = lastOccupied;
            this.secondLastOccupied = secondLastOccupied;
            this.seatCounter = seatCounter;
            this.firstFlag = firstFlag;
            this.patternForSeat = patternForSeat;
        }
    }

}
