package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.room.Room;

import java.util.ArrayList;
import java.util.List;

public class BlocksCreationDto {
    private Room roomReference;
    private List<List<Boolean>> isSeats;

    public BlocksCreationDto() {
    }

    public BlocksCreationDto(Room roomReference, List<List<Boolean>> isSeats) {
        this.roomReference = roomReference;
        this.isSeats = isSeats;
    }

    public Room getRoomReference() {
        return roomReference;
    }

    public void setRoomReference(Room roomReference) {
        this.roomReference = roomReference;
    }

    public List<List<Boolean>> getIsSeats() {
        return isSeats;
    }

    public void setIsSeats(List<List<Boolean>> isSeats) {
        this.isSeats = isSeats;
    }

    public void addBlockRow(List<Boolean> blockRow) {
        this.isSeats.add(blockRow);
    }

    public List<Boolean> getBlockRow(int blockRowNumber) {
        return isSeats.get(blockRowNumber);
    }

    public BlocksCreationDto prepareBlockListsAndFillWithFalse(Room room) {
        BlocksCreationDto blocks = new BlocksCreationDto(room, new ArrayList<>());
        for (int i = 0; i < room.getNumberOfRows(); i++) {
            List<Boolean> blockRow = new ArrayList<>();
            for (int j = 0; j < room.getNumberOfColumns(); j++) {
                blockRow.add(true);
            }
            blocks.addBlockRow(blockRow);
        }
        return blocks;
    }
}
