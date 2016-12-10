package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by admir on 09.12.2016..
 */

/**
 * Na ovaj fazon se prave tabele u bazi,
 * napravite klasu i dodajete anotacije
 * kada koristimo orm, klase koje su mapirane sa tabelama se zovu entity
 * zato da oznacimo neku klasu kao tabelu stavljamo @Entity anotaciju
 * sta onda dalje?
 * Pravite sve fieldove koje zelite da klasa ima i oznacite ih kao kolone
 * nikad nemojte zaboraviti ID kolonu oznacit sa @Id i @GeneratedValue anotacijom,
 * to morate stavit da bi hibernate prepozno da je to id
 * sve ostale fieldove samo stavite @Column anotaciju to je potpuno dovoljno
 * ima jos raznih anotacija za razne opcije, ali ako budu negdje potrebne
 * ja cu ih dodat vi se ne brinite, ja i kad napravit fieldove (instance varijable)
 * zovite kako hocete generisite sa intellij gettere i settere
 */


@Entity
@Table(name = "USER")
public class User implements Serializable {
    @Id @GeneratedValue
    @Column
    private int ID;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private Date birthDate;
    @Column
    private Date started;
    @Column
    private int job; // Has to be on of the position defined in Position class
    @Column
    private double monthlyPay;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int position) {
        this.job = position;
    }

    public double getMonthlyPay() {
        return monthlyPay;
    }

    public void setMonthlyPay(double monthlyPay) {
        this.monthlyPay = monthlyPay;
    }
}
