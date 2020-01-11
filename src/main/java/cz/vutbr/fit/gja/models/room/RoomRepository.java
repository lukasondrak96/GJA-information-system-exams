package cz.vutbr.fit.gja.models.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Repository class of Room
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    /**
     * Finds room by room id
     * @param id Room id
     * @return Room if some is found, null otherwise
     */
    Room findByIdRoom(int id);

    /**
     * Finds room by room number
     * @param roomNumber Room number
     * @return Room if some is found, null otherwise
     */
    Room findByRoomNumber(String roomNumber);

    /**
     * Deletes specific room from database by id
     * @param id Room id
     * @return Number of deleted rooms
     */
    @Transactional
    Long deleteByIdRoom(int id);

    /**
     * Deletes specific room from database by room number
     * @param roomNumber Room number
     * @return Number of deleted rooms
     */
    @Transactional
    Long deleteByRoomNumber(String roomNumber);
}
