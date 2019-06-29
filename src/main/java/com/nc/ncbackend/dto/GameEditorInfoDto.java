package com.nc.ncbackend.dto;

import com.nc.ncbackend.pojo.Game;
import com.nc.ncbackend.pojo.GameMode;
import com.nc.ncbackend.pojo.Task;
import com.nc.ncbackend.pojo.TaskTemplate;

import java.time.LocalDateTime;
import java.util.List;

public class GameEditorInfoDto {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Long getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Long periodicity) {
        this.periodicity = periodicity;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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

    private long id;
    private String name;
    private String description;
    private int maxPlayers;
    private GameMode mode;
    private LocalDateTime startDate;
    private Long periodicity;
    private List<Task> tasks;
    private boolean active;

    public GameEditorInfoDto(Game game){
        id = game.getId();
        name = game.getName();
        description = game.getDescription();
        maxPlayers = game.getMaxPlayers();
        mode = game.getGameMode();
        active = game.isActive();
        tasks = game.getTasks();
    }
}
