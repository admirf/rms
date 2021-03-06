package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Order entity
 */

@Entity
@Table(name="TABLE_ORDER")
public class Order implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private int ID;

    @OneToOne
    @JoinColumn
    private Item item;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Tables table;

    @Column
    private double quantity;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object object) {
        if(object == null || object.getClass() != this.getClass()) return false;
        Order o = (Order) object;
        return o.getID() == this.getID();
    }
}
