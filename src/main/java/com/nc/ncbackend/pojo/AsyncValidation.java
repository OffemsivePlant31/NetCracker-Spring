package com.nc.ncbackend.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class AsyncValidation {

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    private long id;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_gametask")
    private GameTask gameTask;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_photo")
    private Photo photo;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_person")
    private Person auditor;

    private int state;
    private String message;

    public AsyncValidation() {
    }

    public AsyncValidation(GameTask gameTask, Photo photo, Person auditor) {
        this.gameTask = gameTask;
        this.photo = photo;
        this.auditor = auditor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GameTask getGameTask() {
        return gameTask;
    }

    public void setGameTask(GameTask gameTask) {
        this.gameTask = gameTask;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Person getAuditor() {
        return auditor;
    }

    public void setAuditor(Person auditor) {
        this.auditor = auditor;
    }

}
