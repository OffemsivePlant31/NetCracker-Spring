package com.nc.ncbackend.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GameInstance {

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    private long id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private boolean publicGame;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_game")
    private Game game;

    @OneToMany( cascade = CascadeType.ALL )
    @JoinColumn(name="id_gameinstance")
    private List<Team> teams;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_person")
    private Person overseer;


    private int lastMilestone;

    public GameInstance(){

    }

    public GameInstance(LocalDateTime startDate, LocalDateTime endDate, Game game, Person overseer) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.game = game;
        this.overseer = overseer;
    }

    @Deprecated
    public GameInstance(long id, LocalDateTime startDate, LocalDateTime endDate, Game game) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.game = game;
    }

    public boolean isPublicGame() {
        return publicGame;
    }

    public void setPublicGame(boolean publicGame) {
        this.publicGame = publicGame;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Team> getTeams() {
        if(teams == null) teams = new ArrayList<>();
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Person getOverseer() {
        return overseer;
    }

    public void setOverseer(Person overseer) {
        this.overseer = overseer;
    }

    public int getLastMilestone() {
        return lastMilestone;
    }

    public void setLastMilestone(int lastMilestone) {
        this.lastMilestone = lastMilestone;
    }
}
