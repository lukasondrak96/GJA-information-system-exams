package cz.vutbr.fit.gja.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "block")
public class Block {
    @Id
    @NotNull(message="Id bloku je povinné")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_block")
    private int idBlock;

    @Column(name = "is_seat")
    private boolean isSeat;

    @Column(name = "column_number")
    private int columnNumber;

    @Column(name = "row_number")
    private int rowNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_number_containing_block", referencedColumnName = "room_number")
    private Room roomNumberContainingBlock;

    @OneToMany(mappedBy = "block")
    private Set<BlockOnExamRun> blocksOnRun;

    public Block() {

    }

    public Block(boolean isSeat, int columnNumber, int rowNumber, Room roomNumberContainingBlock) {
        this.isSeat = isSeat;
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
        this.roomNumberContainingBlock = roomNumberContainingBlock;
    }

    public int getIdBlock() {
        return idBlock;
    }

    public void setIdBlock(int idBlock) {
        this.idBlock = idBlock;
    }

    public boolean isSeat() {
        return isSeat;
    }

    public void setSeat(boolean seat) {
        this.isSeat = seat;
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

    public Room getRoomNumberContainingBlock() {
        return roomNumberContainingBlock;
    }

    public void setRoomNumberContainingBlock(Room roomNumberContainingBlock) {
        this.roomNumberContainingBlock = roomNumberContainingBlock;
    }
}
