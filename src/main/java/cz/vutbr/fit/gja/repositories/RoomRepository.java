package cz.vutbr.fit.gja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cz.vutbr.fit.gja.models.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    Room findByRoomNumber(String roomNumber);
}
