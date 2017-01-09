package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Notification entity
 */

@Entity
@Table(name = "NOTIFICATION")
public class Notification implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private int ID;

    @Column
    private boolean checked;

    @Column
    private String message;

    public Notification() {
        checked = false;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isRead() {
        return checked;
    }

    public void setRead(boolean read) {
        this.checked = read;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        return ID == that.ID;
    }

}
