package cz.vutbr.fit.gja.services;

import cz.vutbr.fit.gja.models.Room;

public interface RoomServiceDao {
    boolean isRoomAlreadyCreated(Room room);
}
