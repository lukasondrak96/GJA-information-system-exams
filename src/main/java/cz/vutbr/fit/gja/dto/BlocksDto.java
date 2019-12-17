package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.room.Room;

import java.util.ArrayList;
import java.util.List;

public class BlocksDto {
    private Room roomReference;
    private List<List<Boolean>> isSeatList;

    public BlocksDto() {
    }

    public BlocksDto(Room roomReference, List<List<Boolean>> isSeatList) {
        this.roomReference = roomReference;
        this.isSeatList = isSeatList;
    }

    public Room getRoomReference() {
        return roomReference;
    }

    public void setRoomReference(Room roomReference) {
        this.roomReference = roomReference;
    }

    public List<List<Boolean>> getIsSeatList() {
        return isSeatList;
    }

    public void setIsSeatList(List<List<Boolean>> isSeatList) {
        this.isSeatList = isSeatList;
    }

    public void addBlockRow(List<Boolean> blockRow) {
        this.isSeatList.add(blockRow);
    }

    public List<Boolean> getBlockRow(int blockRowNumber) {
        return isSeatList.get(blockRowNumber);
    }

    public BlocksDto prepareBlockListsAndFillWithFalse(Room room) {
        BlocksDto blocks = new BlocksDto(room, new ArrayList<>());
        for (int i = 0; i < room.getNumberOfRows(); i++) {
            List<Boolean> blockRow = new ArrayList<>();
            for (int j = 0; j < room.getNumberOfColumns(); j++) {
                blockRow.add(true);
            }
            blocks.addBlockRow(blockRow);
        }
        return blocks;
    }

    public void createEmptyIsSeatList(Room room) {
        List<List<Boolean>> isSeatList = new ArrayList<>();
        for (int i = 0; i < room.getNumberOfRows(); i++) {
            List<Boolean> isSeatRow = new ArrayList<>();
            for (int j = 0; j < room.getNumberOfColumns(); j++) {
                isSeatRow.add(false);
            }
            isSeatList.add(isSeatRow);
        }
        setIsSeatList(isSeatList);
    }
}
