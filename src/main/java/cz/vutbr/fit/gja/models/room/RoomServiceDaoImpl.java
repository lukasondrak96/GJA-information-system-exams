package cz.vutbr.fit.gja.models.room;

import cz.vutbr.fit.gja.models.exam.ExamRepository;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.examRun.ExamRunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceDaoImpl implements RoomServiceDao {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ExamRunRepository examRunRepository;

    @Autowired
    ExamRepository examRepository;

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
        if (deleteRoom(userEmail, room)) return 0;

        return roomRepository.deleteByIdRoom(id);
    }

    @Override
    public long deleteRoomByRoomNumber(String roomNumber) throws IllegalAccessError {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Room room = roomRepository.findByRoomNumber(roomNumber);
        if (deleteRoom(userEmail, room)) return 0;

        if (!userEmail.equals(room.getTeacherReference().getEmail())) {
            throw new IllegalAccessError("Nelze smazat místnost jiného uživatele");
        }
        return roomRepository.deleteByRoomNumber(roomNumber);
    }

    private boolean deleteRoom(String userEmail, Room room) {
        if(room == null) {
            return true;
        }
        if (!userEmail.equals(room.getTeacherReference().getEmail())) {
            throw new IllegalAccessError("Nelze smazat místnost jiného uživatele");
        }

        List<ExamRun> examRunsOfRoom = examRunRepository.findAllByRoomReference(room);
        for (ExamRun run: examRunsOfRoom) {
            examRepository.deleteByIdExam(run.getExamReference().getIdExam());
        }
        return false;
    }
}
