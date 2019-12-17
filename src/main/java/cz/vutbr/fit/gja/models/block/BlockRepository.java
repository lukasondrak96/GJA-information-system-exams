package cz.vutbr.fit.gja.models.block;

import cz.vutbr.fit.gja.models.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Integer> {
    Long countAllByRoomReferenceAndSeatTrue(Room room);

    List<Block> findByRoomReference(Room room);
}
