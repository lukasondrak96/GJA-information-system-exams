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

    public void createIsSeatListOfSeats(Room room) {
        this.roomReference = room;
        fillList(room,true);
    }

    public void createIsSeatListOfAisles(Room room) {
        this.roomReference = room;
        fillList(room,false);
    }

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
