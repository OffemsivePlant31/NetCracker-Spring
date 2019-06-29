package com.nc.ncbackend.pojo.notification;

import com.nc.ncbackend.pojo.GameInstance;
import com.nc.ncbackend.pojo.Person;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class GameNotification {

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    private long id;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_gameinstance")
    private GameInstance gameInstance;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_person")
    private Person person;

    private boolean checked;
    private int minutes;

    public GameNotification(GameInstance gameInstance, Person person, int minutes) {
        this.gameInstance = gameInstance;
        this.person = person;
        this.checked = false;
        this.minutes = minutes;
    }

    public GameNotification() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GameInstance getGameInstance() {
        return gameInstance;
    }

    public void setGameInstance(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
