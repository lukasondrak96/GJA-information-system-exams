package cz.vutbr.fit.gja.models.block;

import cz.vutbr.fit.gja.dto.BlocksCreationDto;
import cz.vutbr.fit.gja.models.room.Room;

public interface BlockServiceDao {
    void createAndSaveBlocksForRoom(Room room, BlocksCreationDto blocks);
}
