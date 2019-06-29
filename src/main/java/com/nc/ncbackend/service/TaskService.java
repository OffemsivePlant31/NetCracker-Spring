package com.nc.ncbackend.service;

import com.nc.ncbackend.pojo.Game;
import com.nc.ncbackend.pojo.Task;

import java.util.List;

public interface TaskService {
    List<Task> findByGame(Game game);
    Task find(long id);
    void save(Task task);
    Task findLastByGame(Game game);
    void delete(long id);
}
