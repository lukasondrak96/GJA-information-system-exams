package cz.vutbr.fit.gja.models;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    private String id;
    private int numberOfRow;
    private int numberofColumn;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user_room", referencedColumnName = "email")
    User user;

    public Room() {

    }

    public Room(String id, int numberOfRow, int numberofColumn) {
        this.id = id;
        this.numberOfRow = numberOfRow;
        this.numberofColumn = numberofColumn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumberOfRow() {
        return numberOfRow;
    }

    public void setNumberOfRow(int numberOfRow) {
        this.numberOfRow = numberOfRow;
    }

    public int getNumberofColumn() {
        return numberofColumn;
    }

    public void setNumberofColumn(int numberofColumn) {
        this.numberofColumn = numberofColumn;
    }


}
