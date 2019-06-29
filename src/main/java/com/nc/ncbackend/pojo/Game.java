package com.nc.ncbackend.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    private long id;

    private String name;
    private String description;
    private int maxPlayers;

    private boolean active;
    private boolean publicTemplate;

    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn(name="id_author")
    private Person author;

    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn(name="id_gamemode")
    private GameMode gameMode;

    @OneToMany( cascade = CascadeType.PERSIST )
    @JoinColumn(name="id_game")
    private List<Task> tasks;

    @OneToMany( cascade = CascadeType.PERSIST )
    @JoinColumn(name="id_game")
    private List<GamePeriod> periods;

    @OneToMany( cascade = CascadeType.PERSIST )
    @JoinColumn(name="id_game")
    private List<GameRating> rating;

    public Game() {
        tasks = new ArrayList<>(); // не помню, зачем это
    }

    @Deprecated
    public Game(long id, String name, int maxPlayers,Person author, GameMode gameMode) {
        this();
        this.id = id;
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.author = author;
        this.gameMode = gameMode;
    }

    public Game(String name, int maxPlayers) {
        this();
        this.name = name;
        this.maxPlayers = maxPlayers;
    }

    public boolean isPublicTemplate() {
        return publicTemplate;
    }

    public void setPublicTemplate(boolean publicTemplate) {
        this.publicTemplate = publicTemplate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public List<Task> getTasks() {
        if(tasks == null) tasks = new ArrayList<>();
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<GamePeriod> getPeriods() {
        if(periods == null) periods = new ArrayList<>();
        return periods;
    }

    public void setPeriods(List<GamePeriod> periods) {
        this.periods = periods;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GameRating> getRating() {
        if(rating == null) rating = new ArrayList<>();
        return rating;
    }

    public void setRating(List<GameRating> rating) {
        this.rating = rating;
    }
}
