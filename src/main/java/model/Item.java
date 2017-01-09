package model;

import javax.persistence.*;

/**
 * Item entity
 */

@Entity
@Table(name="ITEM")
public class Item {
    @Id
    @GeneratedValue
    @Column
    private int ID;

    @Column
    private double cost;

    @Column
    private double price;

    @Column
    private String name;

    @Column
    private String imgUrl;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj.getClass() != this.getClass()) return false;

        Item o = (Item) obj;
        return o.getID() == this.getID();
    }
}
