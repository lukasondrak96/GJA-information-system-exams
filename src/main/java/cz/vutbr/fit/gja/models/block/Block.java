package cz.vutbr.fit.gja.models.block;

import cz.vutbr.fit.gja.models.blockOnExamRun.BlockOnExamRun;
import cz.vutbr.fit.gja.models.room.Room;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "block")
public class Block {
    @Id
    @NotNull(message="Id bloku je povinné")
    @SequenceGenerator(name = "BlockIdGenerator", sequenceName = "BLOCK_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BlockIdGenerator")
    @Column(name = "id_block")
    private int idBlock;

    @Column(name = "seat")
    private boolean seat;

    @Column(name = "column_number")
    private int columnNumber;

    @Column(name = "row_number")
    private int rowNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "room_reference", referencedColumnName = "room_number")
    private Room roomReference;

    @OneToMany(mappedBy = "block")
    private Set<BlockOnExamRun> blocksOnRun;

    public Block() {

    }

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
