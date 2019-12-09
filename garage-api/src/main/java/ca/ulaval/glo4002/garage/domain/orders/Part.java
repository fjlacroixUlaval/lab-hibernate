package ca.ulaval.glo4002.garage.domain.orders;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "parts")
@Table
public class Part implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private final int quantity;
    @Column
    private final String name;

    public Part(int quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    public Part() {
        this.name = null;
        this.quantity = 0;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
