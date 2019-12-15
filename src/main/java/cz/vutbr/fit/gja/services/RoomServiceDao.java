package cz.vutbr.fit.gja.services;

import cz.vutbr.fit.gja.models.Room;

import java.util.List;

public interface RoomServiceDao {
    boolean isRoomAlreadyCreated(Room room);

    List<Room> getAllRoomsFromDatabase();

    Room getRoom(String name);

    void saveRoomToDatabase(Room room);
}
