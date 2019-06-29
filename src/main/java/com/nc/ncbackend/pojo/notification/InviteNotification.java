package com.nc.ncbackend.pojo.notification;

import com.nc.ncbackend.pojo.GameInstance;
import com.nc.ncbackend.pojo.Person;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class InviteNotification {


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

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_sender")
    private Person sender;

    private boolean checked;
    private boolean closed;

    public InviteNotification(GameInstance gameInstance, Person person, Person sender) {
        this.gameInstance = gameInstance;
        this.person = person;
        this.sender = sender;
    }

    public InviteNotification() {
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

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
