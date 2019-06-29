package com.nc.ncbackend.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    private long id;

    private float result;
    private LocalDateTime dateRegistered;
    private boolean gameStarted;

    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn(name="id_gameinstance")
    private GameInstance game;

    @OneToMany( cascade = CascadeType.ALL )
    @JoinColumn(name="id_team")
    private List<GameTask> tasks;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "PersonInTeam",
            joinColumns = { @JoinColumn(name = "id_team") },
            inverseJoinColumns = { @JoinColumn(name = "id_person") })
    private List<Person> users;

    public Team() {
    }

    public Team(float result, LocalDateTime dateRegistered, boolean gameStarted) {
        this.result = result;
        this.dateRegistered = dateRegistered;
        this.gameStarted = gameStarted;
    }

    @Deprecated
    public Team(long id, float result, LocalDateTime dateRegistered, boolean gameStarted) {
        this.id = id;
        this.result = result;
        this.dateRegistered = dateRegistered;
        this.gameStarted = gameStarted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }

    public LocalDateTime getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(LocalDateTime dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public GameInstance getGame() {
        return game;
    }

    public void setGame(GameInstance game) {
        this.game = game;
    }

    public List<Person> getUsers() {
        if(users == null) users = new ArrayList<>();
        return users;
    }

    public void setUsers(List<Person> users) {
        this.users = users;
    }

    public List<GameTask> getTasks() {
        if(tasks == null) tasks = new ArrayList<>();
        return tasks;
    }

    public void setTasks(List<GameTask> tasks) {
        this.tasks = tasks;
    }
}
