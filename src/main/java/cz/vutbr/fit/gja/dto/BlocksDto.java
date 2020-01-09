package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.room.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents all blocks of one specific room
 */
public class BlocksDto {
    /**
     * Room in which are blocks located
     */
    private Room roomReference;

    /**
     * 2D List of block values (true - seat, false - aisle)
     * Size of this list represents size of room
     */
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

    /**
     * Adds new row to list of blocks
     * @param blockRow List of blocks, which represents one row in room
     */
    public void addBlockRow(List<Boolean> blockRow) {
        this.isSeatList.add(blockRow);
    }

    /**
     * Gets row of room
     * @param blockRowNumber Row number
     * @return List of block, which represents row of room
     */
    public List<Boolean> getBlockRow(int blockRowNumber) {
        return isSeatList.get(blockRowNumber);
    }

    /**
     * Creates list of blocks from given room - all block will be seats
     * @param room Room for which will blocks be created
     */
    public void createIsSeatListOfSeats(Room room) {
        this.roomReference = room;
        fillList(room,true);
    }

    /**
     * Creates list of blocks from given room - all block will be aisles
     * @param room Room for which will blocks be created
     */
    public void createIsSeatListOfAisles(Room room) {
        this.roomReference = room;
        fillList(room,false);
    }

    /**
     * Replaces blocks with null value with aisles (value = false)
     */
    public void replaceNullValuesWithFalse() {
        Room room = this.roomReference;
        for (int i = 0; i < room.getNumberOfRows(); i++) {
            List<Boolean> isSeatRow = this.isSeatList.get(i);
            for (int j = 0; j < room.getNumberOfColumns(); j++) {
                try {
                    if(isSeatRow.get(j) == null) {
                        isSeatRow.set(j, false);
                    }
                } catch (IndexOutOfBoundsException e) {
                    isSeatRow.add(false);
                }
            }
            getIsSeatList().add(isSeatRow);
        }
    }

    /**
     * Creates 2D list of blocks, which represents room and fills it with given value
     * @param room Room in which blocks are located
     * @param fillWith Value to be set to each block (true - seats, false = aisles)
     */
    private void fillList(Room room, Boolean fillWith) {
        this.isSeatList = new ArrayList<>();
        for (int i = 0; i < room.getNumberOfRows(); i++) {
            List<Boolean> isSeatRow = new ArrayList<>();
            for (int j = 0; j < room.getNumberOfColumns(); j++) {
                isSeatRow.add(fillWith);
            }
            getIsSeatList().add(isSeatRow);
        }
        setIsSeatList(this.isSeatList);
    }
}
