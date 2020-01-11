package cz.vutbr.fit.gja.models.block;

import cz.vutbr.fit.gja.models.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class encapsulates all methods for the Block entity that communicates with the database
 */
@Repository
public interface BlockRepository extends JpaRepository<Block, Integer> {
    /**
     * Counts all seats in given room
     * @param room Room from which the number of seats should be obtained
     * @return Number of seats in given room
     */
    Long countAllByRoomReferenceAndSeatTrue(Room room);

    /**
     * Gets all blocks in given room
     * @param room Room from which the blocks should be obtained
     * @return All blocks from given room
     */
    List<Block> findByRoomReference(Room room);
}
