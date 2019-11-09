package cz.vutbr.fit.gja.services;

import cz.vutbr.fit.gja.models.Room;
import cz.vutbr.fit.gja.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceDaoImpl implements RoomServiceDao {

    @Autowired
    RoomRepository roomRepository;

    @Override
    public boolean isRoomAlreadyCreated(Room room) {
        String roomNumber = room.getRoomNumber();
        Room foundRoom = roomRepository.findByRoomNumber(roomNumber);
        return foundRoom != null;
    }
}
