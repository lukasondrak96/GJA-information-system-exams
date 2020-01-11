package cz.vutbr.fit.gja.models.block;

import cz.vutbr.fit.gja.dto.BlocksDto;
import cz.vutbr.fit.gja.dto.RoomAndNumberOfSeatsDto;
import cz.vutbr.fit.gja.models.room.Room;

import java.util.List;

public interface BlockServiceDao {
    /**
     * Saves blocks to database
     * @param room The room to which the blocks are related
     * @param blocks Blocks to be saved
     */
    void createAndSaveBlocksForRoom(Room room, BlocksDto blocks);

    /**
     * Counts all seats in given room
     * @param roomReference Room from which the number of seats should be obtained
     * @return Number of seats in given room
     */
    long getNumberOfSeats(Room roomReference);

    /**
     * Gets all rooms from database and their capacities
     * @return List of rooms and their capacities
     */
    List<RoomAndNumberOfSeatsDto> getRoomAndNumberOfSeatsOfAllRooms();

    /**
     * Gets all blocks of given room and saves them in BlocksDto class
     * @param room Room from which the blocks should be obtained
     * @return BlocksDto object with room and all her blocks
     */
    BlocksDto getAllBlocksOfRoomAsDto(Room room);

    /**
     * Gets all blocks of given room
     * @param room Room from which the blocks should be obtained
     * @return List of all blocks of given room
     */
    List<Block> getAllBlocks(Room room);
}
