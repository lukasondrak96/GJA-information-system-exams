package cz.vutbr.fit.gja.services;

import cz.vutbr.fit.gja.models.Room;
import cz.vutbr.fit.gja.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceDaoImpl implements RoomServiceDao {

    @Autowired
    RoomRepository roomRepository;

    @Override
    public boolean isRoomAlreadyCreated(Room room) {
        String roomNumber = room.getRoomNumber();
        Room foundRoom = getRoom(roomNumber);
        return foundRoom != null;
    }

    @Override
    public List<Room> getAllRoomsFromDatabase() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoom(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }

    @Override
    public void saveRoomToDatabase(Room room) {
        roomRepository.save(room);
    }
}
