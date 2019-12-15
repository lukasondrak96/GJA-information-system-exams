package cz.vutbr.fit.gja.models.room;

import java.util.List;

public interface RoomServiceDao {
    boolean isRoomAlreadyCreated(Room room);

    List<Room> getAllRoomsFromDatabase();

    Room getRoom(String name);

    void saveRoomToDatabase(Room room);
}
