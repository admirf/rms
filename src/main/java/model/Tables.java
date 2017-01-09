package model;

import javax.persistence.*;
import java.util.Set;

/**
 * Tables entity
 */
@Entity
@Table(name="TABLES")
public class Tables {
    @Id
    @GeneratedValue
    @Column
    private int ID;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "table")
    private Set<Order> orders;

    @Column
    private boolean occupied;

    @Column
    private int name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tables tables = (Tables) o;

        return ID == tables.ID;
    }

}
