package cz.vutbr.fit.gja.models.block;


import cz.vutbr.fit.gja.dto.BlocksDto;
import cz.vutbr.fit.gja.dto.RoomAndNumberOfSeatsDto;
import cz.vutbr.fit.gja.models.room.Room;
import cz.vutbr.fit.gja.models.room.RoomServiceDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockServiceDaoImpl implements BlockServiceDao {
    @Autowired
    BlockRepository blockRepository;

    @Autowired
    RoomServiceDaoImpl roomServiceDao;

    @Override
    public void createAndSaveBlocksForRoom(Room room, BlocksDto blocks) {
        for (int i = 0; i < room.getNumberOfRows(); i++) {
            for (int j = 0; j < room.getNumberOfColumns(); j++) {
                List<Boolean> blockRow = new ArrayList<>();
                try {
                    blockRow = blocks.getBlockRow(i);
                } catch (IndexOutOfBoundsException e) {
                    blocks.addBlockRow(blockRow);
                }

                Boolean isSeat = true;
                try {
                    isSeat = blocks.getBlockRow(i).get(j);
                } catch (IndexOutOfBoundsException e) {
                    isSeat = false;
                    blocks.getBlockRow(i).add(false);
                } finally {
                    if (isSeat == null) {
                        isSeat = false;
                        blocks.getBlockRow(i).set(j, false);
                    }
                }

                Block block = new Block(isSeat, j + 1, room.getNumberOfRows() - i, room);
                blockRepository.save(block);
            }
        }
    }

    @Override
    public long getNumberOfSeats(Room roomReference) {
        return blockRepository.countAllByRoomReferenceAndSeatTrue(roomReference);
    }

    @Override
    public List<RoomAndNumberOfSeatsDto> getRoomAndNumberOfSeatsOfAllRooms() {
        List<RoomAndNumberOfSeatsDto> roomAndNumberOfSeatsDtoList = new ArrayList<>();
        List<Room> rooms = roomServiceDao.getAllRoomsFromDatabase();
        for (Room room : rooms) {
            long numberOfSeats = getNumberOfSeats(room);
            roomAndNumberOfSeatsDtoList.add(new RoomAndNumberOfSeatsDto(room, numberOfSeats));
        }
        return roomAndNumberOfSeatsDtoList;
    }

    @Override
    public BlocksDto getAllBlocksOfRoom(Room room) {
        BlocksDto blocksDto = new BlocksDto();
        blocksDto.createEmptyIsSeatList(room);
        blocksDto.setRoomReference(room);

        List<Block> blockList = blockRepository.findByRoomReference(room);
        for (Block block : blockList) {
            blocksDto.getBlockRow(block.getRowNumber() - 1).set(block.getColumnNumber() - 1, block.isSeat());
        }
        return blocksDto;
    }
}
