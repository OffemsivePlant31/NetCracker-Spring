package com.nc.ncbackend.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn(name="id_gamepoint")
    private GamePoint point;

    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn(name="id_findcondition")
    private FindCondition findCondition;

    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn(name="id_checkcondition")
    private CheckCondition checkCondition;

    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn(name="id_game")
    private Game game;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "TaskLink",
            joinColumns = { @JoinColumn(name = "id_prev") },
            inverseJoinColumns = { @JoinColumn(name = "id_next") })
    private List<Task> nextTasks;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "TaskLink",
            joinColumns = { @JoinColumn(name = "id_next") },
            inverseJoinColumns = { @JoinColumn(name = "id_prev") })
    private List<Task> prevTasks;

    public Task(){

    }

    public Task(GamePoint point, FindCondition findCondition, CheckCondition checkCondition) {
        this.point = point;
        this.findCondition = findCondition;
        this.checkCondition = checkCondition;
    }

    @Deprecated
    public Task(long id, GamePoint point, FindCondition findCondition, CheckCondition checkCondition) {
        this.id = id;
        this.point = point;
        this.findCondition = findCondition;
        this.checkCondition = checkCondition;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GamePoint getPoint() {
        return point;
    }

    public void setPoint(GamePoint point) {
        this.point = point;
    }

    public FindCondition getFindCondition() {
        return findCondition;
    }

    public void setFindCondition(FindCondition findCondition) {
        this.findCondition = findCondition;
    }

    public CheckCondition getCheckCondition() {
        return checkCondition;
    }

    public void setCheckCondition(CheckCondition checkCondition) {
        this.checkCondition = checkCondition;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Task> getNextTasks() {
        if(nextTasks == null) nextTasks = new ArrayList<>();
        return nextTasks;
    }

    public void setNextTasks(List<Task> nextTasks) {
        this.nextTasks = nextTasks;
    }

    public List<Task> getPrevTasks() {
        if(prevTasks == null) prevTasks = new ArrayList<>();
        return prevTasks;
    }

    public void setPrevTasks(List<Task> prevTasks) {
        this.prevTasks = prevTasks;
    }
}
