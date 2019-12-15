package cz.vutbr.fit.gja.services;

import cz.vutbr.fit.gja.dto.BlocksCreationDto;
import cz.vutbr.fit.gja.models.Room;

public interface BlockServiceDao {
    void createAndSaveBlocksForRoom(Room room, BlocksCreationDto blocks);
}
