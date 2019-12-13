package cz.vutbr.fit.gja.services;

import cz.vutbr.fit.gja.models.Room;

import java.util.List;

public interface RoomServiceDao {
    boolean isRoomAlreadyCreated(Room room);

    List<Room> getAllRooms();

    Room getSpecificRoom(String roomNumber);

    void saveRoom(Room room);
}
