package cz.vutbr.fit.gja.models.block;

import cz.vutbr.fit.gja.models.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Integer> {
    Long countAllByRoomReferenceAndSeatTrue(Room room);
}
