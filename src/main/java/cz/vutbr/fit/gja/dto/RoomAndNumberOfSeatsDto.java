package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.room.Room;

public class RoomAndNumberOfSeatsDto {

    private Room room;
    private long numberOfSeats;

    public RoomAndNumberOfSeatsDto() {
    }

    public RoomAndNumberOfSeatsDto(Room room, long numberOfSeats) {
        this.room = room;
        this.numberOfSeats = numberOfSeats;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
