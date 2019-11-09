package cz.vutbr.fit.gja.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @NotNull(message="Jméno místnosti je povinné")
    @Column(name = "room_number")
    private String roomNumber;

    @Min(value=1, message="Počet řad musí být větší než 0")
    @Column(name = "number_of_rows")
    private int numberOfRows;

    @Min(value=1, message="Počet bloků v řadě musí být větší než 0")
    @Column(name = "number_of_columns")
    private int numberOfColumns;

    @ManyToOne()
    @JoinColumn(name = "id_room_creator", referencedColumnName = "email")
    private User roomCreator;

    public Room() {

    }

    public Room(String roomNumber, int numberOfRows, int numberOfColumns) {
        this.roomNumber = roomNumber;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public User getRoomCreator() {
        return roomCreator;
    }

    public void setRoomCreator(User roomCreator) {
        this.roomCreator = roomCreator;
    }
}
