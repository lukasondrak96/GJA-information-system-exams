package cz.vutbr.fit.gja.hello;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HelloObject {

    @Id
    private int id;
    private String name;
    private float numberWithDecimalPart;

    public HelloObject() {

    }

    public HelloObject(int number, String name, float numberWithDecimalPart) {
        this.id = number;
        this.name = name;
        this.numberWithDecimalPart = numberWithDecimalPart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getNumberWithDecimalPart() {
        return numberWithDecimalPart;
    }

    public void setNumberWithDecimalPart(float numberWithDecimalPart) {
        this.numberWithDecimalPart = numberWithDecimalPart;
    }
}
