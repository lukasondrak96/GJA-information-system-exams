package cz.vutbr.fit.gja.models.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    Room findByRoomNumber(String roomNumber);

    @Transactional
    Long deleteByRoomNumber(String roomNumber);
}
