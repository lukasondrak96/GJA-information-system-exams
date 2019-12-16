package cz.vutbr.fit.gja.models.room;

import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.teacher.Teacher;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @NotEmpty(message = "Jméno místnosti je povinné")
    @NotNull(message="Jméno místnosti je povinné")
    @Column(name = "room_number")
    private String roomNumber;

    @Min(value=1, message="Počet řad musí být větší než 0")
    @Column(name = "number_of_rows")
    private int numberOfRows;

    @Min(value=1, message="Počet bloků v řadě musí být větší než 0")
    @Column(name = "number_of_columns")
    private int numberOfColumns;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_room_creator", referencedColumnName = "email")
    private Teacher idRoomCreator;

    @OneToMany(mappedBy = "roomNumber")
    private Set<ExamRun> exam_runs;

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

    public Teacher getRoomCreator() {
        return idRoomCreator;
    }

    public void setRoomCreator(Teacher roomCreator) {
        this.idRoomCreator = roomCreator;
    }
}
