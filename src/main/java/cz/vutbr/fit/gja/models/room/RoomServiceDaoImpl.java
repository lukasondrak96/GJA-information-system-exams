package cz.vutbr.fit.gja.models.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceDaoImpl implements RoomServiceDao {

    @Autowired
    RoomRepository roomRepository;

    @Override
    public boolean isRoomAlreadyCreated(Room room) {
        String roomNumber = room.getRoomNumber();
        Room foundRoom = getRoomByRoomNumber(roomNumber);
        return foundRoom != null;
    }

    @Override
    public List<Room> getAllRoomsFromDatabase() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(int id) {
        return roomRepository.findByIdRoom(id);
    }

    @Override
    public Room getRoomByRoomNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }

    @Override
    public Room saveRoomToDatabase(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public long deleteRoomById(int id) throws IllegalAccessError {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Room room = roomRepository.findByIdRoom(id);
        if(room == null) {
            return 0;
        }
        if (!userEmail.equals(room.getTeacherReference().getEmail())) {
            throw new IllegalAccessError("Nelze smazat místnost jiného uživatele");
        }
        return roomRepository.deleteByIdRoom(id);
    }

    @Override
    public long deleteRoomByRoomNumber(String roomNumber) throws IllegalAccessError {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Room room = roomRepository.findByRoomNumber(roomNumber);
        if(room == null) {
            return 0;
        }
        if (!userEmail.equals(room.getTeacherReference().getEmail())) {
            throw new IllegalAccessError("Nelze smazat místnost jiného uživatele");
        }
        return roomRepository.deleteByRoomNumber(roomNumber);
    }
}
