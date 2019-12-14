package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.Block;
import cz.vutbr.fit.gja.models.Room;

import java.util.List;

public class BlocksCreationDto {
    private Room roomReference;
    private List<List<Block>> blocks;

    public BlocksCreationDto() {
    }

    public BlocksCreationDto(Room roomReference, List<List<Block>> blocks) {
        this.roomReference = roomReference;
        this.blocks = blocks;
    }

    public Room getRoomReference() {
        return roomReference;
    }

    public void setRoomReference(Room roomReference) {
        this.roomReference = roomReference;
    }

    public List<List<Block>> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<List<Block>> blocks) {
        this.blocks = blocks;
    }

    public void addBlockRow(List<Block> blockRow) {
        this.blocks.add(blockRow);
    }

    public List<Block> getBlockRow(int blockRowNumber) {
        return blocks.get(blockRowNumber);
    }
}
