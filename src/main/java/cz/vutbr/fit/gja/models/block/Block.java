package cz.vutbr.fit.gja.models.block;

import cz.vutbr.fit.gja.models.room.Room;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This class represents entity block. This block can represent aisle or seat in the room
 */
@Entity
@Table(name = "block")
public class Block {
    /**
     * Block ID
     */
    @Id
    @NotNull(message="ID místa je povinné")
    @SequenceGenerator(name = "BlockIdGenerator", sequenceName = "BLOCK_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BlockIdGenerator")
    @Column(name = "id_block")
    private int idBlock;

    /**
     * A value that indicates whether the block is aisle or seat
     * true = seat, false = aisle
     */
    @Column(name = "seat")
    private boolean seat;

    /**
     * The number of the row in which the block is located
     */
    @Column(name = "column_number")
    private int columnNumber;

    /**
     * The number of the row in which the block is located
     */
    @Column(name = "row_number")
    private int rowNumber;

    /**
     * Reference to the room in which the block is located
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "room_reference", referencedColumnName = "id_room")
    private Room roomReference;

//    @OneToMany(mappedBy = "block")
//    private Set<BlockOnExamRun> blocksOnRun;

    /**
     * Creates a new block
     */
    public Block() {

    }

    /**
     * Creates a new block
     * @param seat A value that indicates whether the block is aisle or seat (true = seat, false = aisle)
     * @param columnNumber The number of the row in which the block is located
     * @param rowNumber The number of the row in which the block is located
     * @param roomReference Reference to the room in which the block is located
     */
    public Block(boolean seat, int columnNumber, int rowNumber, Room roomReference) {
        this.seat = seat;
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
        this.roomReference = roomReference;
    }

    public int getIdBlock() {
        return idBlock;
    }

    public void setIdBlock(int idBlock) {
        this.idBlock = idBlock;
    }

    public boolean isSeat() {
        return seat;
    }

    public void setSeat(boolean seat) {
        this.seat = seat;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Room getRoomReference() {
        return roomReference;
    }

    public void setRoomReference(Room roomReference) {
        this.roomReference = roomReference;
    }
}
