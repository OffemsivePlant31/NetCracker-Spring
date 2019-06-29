package com.nc.ncbackend.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Invite {

    public Invite(Person personFrom, Person personTo, GameInstance game) {
        this.personFrom = personFrom;
        this.personTo = personTo;
        this.game = game;
    }

    public Invite(){}

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_personfrom")
    private Person personFrom;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_personto")
    private Person personTo;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_gameinstance")
    private GameInstance game;

    private boolean answered;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getPersonFrom() {
        return personFrom;
    }

    public void setPersonFrom(Person personFrom) {
        this.personFrom = personFrom;
    }

    public Person getPersonTo() {
        return personTo;
    }

    public void setPersonTo(Person personTo) {
        this.personTo = personTo;
    }

    public GameInstance getGame() {
        return game;
    }

    public void setGame(GameInstance game) {
        this.game = game;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
