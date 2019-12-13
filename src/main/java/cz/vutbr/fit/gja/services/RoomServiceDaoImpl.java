package cz.vutbr.fit.gja.services;

import cz.vutbr.fit.gja.Exceptions.RoomNotFoundException;
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
        Room foundRoom = roomRepository.findByRoomNumber(roomNumber);
        return foundRoom != null;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getSpecificRoom(String roomNumber) {
        return roomRepository.findById(roomNumber)
                .orElseThrow(() -> new RoomNotFoundException("číslem", roomNumber));
    }

    @Override
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }
}
