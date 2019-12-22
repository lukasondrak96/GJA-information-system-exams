package cz.vutbr.fit.gja.models.room;

import java.util.List;

public interface RoomServiceDao {
    boolean isRoomAlreadyCreated(Room room);

    List<Room> getAllRoomsFromDatabase();

    Room getRoomById(int id);

    Room getRoomByRoomNumber(String roomNumber);


    Room saveRoomToDatabase(Room room);

    long deleteRoomById(int id) throws IllegalAccessError;

    long deleteRoomByRoomNumber(String roomNumber) throws IllegalAccessError;

}
