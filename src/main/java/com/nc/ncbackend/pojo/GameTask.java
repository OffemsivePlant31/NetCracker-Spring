package com.nc.ncbackend.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GameTask {

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    private long id;

    private LocalDateTime timeDone;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_team")
    private Team team;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_task")
    private Task task;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "GameTaskLink",
            joinColumns = { @JoinColumn(name = "id_prev") },
            inverseJoinColumns = { @JoinColumn(name = "id_next") })
    private List<GameTask> nextTasks;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "GameTaskLink",
            joinColumns = { @JoinColumn(name = "id_next") },
            inverseJoinColumns = { @JoinColumn(name = "id_prev") })
    private List<GameTask> prevTasks;

    public GameTask(){

    }


    public GameTask(LocalDateTime timeDone, Team team, Task task) {
        this.timeDone = timeDone;
        this.team = team;
        this.task = task;
    }

    public GameTask(LocalDateTime timeDone, Task task) {
        this.timeDone = timeDone;
        this.task = task;
    }

    @Deprecated
    public GameTask(long id, LocalDateTime timeDone, Team team, Task task) {
        this.id = id;
        this.timeDone = timeDone;
        this.team = team;
        this.task = task;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTimeDone() {
        return timeDone;
    }

    public void setTimeDone(LocalDateTime timeDone) {
        this.timeDone = timeDone;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<GameTask> getNextTasks() {
        return nextTasks;
    }

    public void setNextTasks(List<GameTask> nextTasks) {
        this.nextTasks = nextTasks;
    }

    public List<GameTask> getPrevTasks() {
        if(prevTasks == null) prevTasks = new ArrayList<>();
        return prevTasks;
    }

    public void setPrevTasks(List<GameTask> prevTasks) {
        this.prevTasks = prevTasks;
    }
}
