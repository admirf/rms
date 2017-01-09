package model;

import javax.persistence.*;
import java.util.Date;

/**
 * Payment Entity
 */

@Entity
@Table(name = "PAYMENT")
public class Payment {
    @Id
    @GeneratedValue
    @Column
    private int ID;

    @Column
    private double value;

    @Column
    private Date timestamp;

    public Payment() {
        timestamp = new Date();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        return ID == payment.ID;
    }

}
