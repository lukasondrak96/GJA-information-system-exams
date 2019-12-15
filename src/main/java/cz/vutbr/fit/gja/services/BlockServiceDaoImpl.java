package cz.vutbr.fit.gja.services;


import cz.vutbr.fit.gja.dto.BlocksCreationDto;
import cz.vutbr.fit.gja.models.Block;
import cz.vutbr.fit.gja.models.Room;
import cz.vutbr.fit.gja.repositories.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BlockServiceDaoImpl implements BlockServiceDao {
    @Autowired
    BlockRepository blockRepository;

    @Override
    public void createAndSaveBlocksForRoom(Room room, BlocksCreationDto blocks) {
        for (int i = 0; i < room.getNumberOfRows(); i++) {
            for (int j = 0; j < room.getNumberOfColumns(); j++) {
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
}
