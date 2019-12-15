package cz.vutbr.fit.gja.models.block;

import cz.vutbr.fit.gja.dto.BlocksCreationDto;
import cz.vutbr.fit.gja.dto.RoomAndNumberOfSeatsDto;
import cz.vutbr.fit.gja.models.room.Room;

import java.util.List;

public interface BlockServiceDao {
    void createAndSaveBlocksForRoom(Room room, BlocksCreationDto blocks);

    long getNumberOfSeats(Room roomReference);

    List<RoomAndNumberOfSeatsDto> getRoomAndNumberOfSeatsOfAllRooms();
}
