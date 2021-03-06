package cz.vutbr.fit.gja.models.room;

import cz.vutbr.fit.gja.models.teacher.Teacher;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * This class represents the entity of room
 */
@Entity
@Table(name = "room")
public class Room {

    /**
     * Room ID
     */
    @Id
    @SequenceGenerator(name = "RoomIdGenerator", sequenceName = "ROOM_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RoomIdGenerator")
    @Column(name = "id_room")
    private int idRoom;

    /**
     * Room number
     */
    @NotEmpty(message = "Jméno místnosti je povinné")
    @NotNull(message="Jméno místnosti je povinné")
    @Column(name = "room_number")
    private String roomNumber;

    /**
     * Number of rows in the room
     */
    @Min(value=1, message="Počet řad musí být větší než 0")
    @Column(name = "number_of_rows")
    private int numberOfRows;

    /**
     * Number of columns in the room
     */
    @Min(value=1, message="Počet bloků v řadě musí být větší než 0")
    @Column(name = "number_of_columns")
    private int numberOfColumns;

    /**
     * Teacher reference (who created the room)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "teacher_reference", referencedColumnName = "email")
    private Teacher teacherReference;

    public Room() {
    }

    /**
     * Creates a new Room
     *
     * @param roomNumber Room number
     * @param numberOfRows  Number of rows in the room
     * @param numberOfColumns Number of columns in the room
     * @param teacherReference Teacher reference
     */
    public Room(String roomNumber, int numberOfRows, int numberOfColumns, Teacher teacherReference) {
        this.roomNumber = roomNumber;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.teacherReference = teacherReference;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
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

    public Teacher getTeacherReference() {
        return teacherReference;
    }

    public void setTeacherReference(Teacher teacherReference) {
        this.teacherReference = teacherReference;
    }
}
