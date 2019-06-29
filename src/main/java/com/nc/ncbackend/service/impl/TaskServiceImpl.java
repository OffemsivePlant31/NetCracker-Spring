package com.nc.ncbackend.service.impl;

import com.nc.ncbackend.pojo.Game;
import com.nc.ncbackend.pojo.Task;
import com.nc.ncbackend.repository.TaskRepository;
import com.nc.ncbackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<Task> findByGame(Game game){
        return taskRepository.findByGame(game);
    }

    @Override
    public Task find(long id){
        return taskRepository.findOne(id);
    }

    @Override
    public void save(Task task){
        taskRepository.save(task);
    }

    @Override
    public Task findLastByGame(Game game){
        return taskRepository.findTopByGameOrderByIdDesc(game);
    }

    @Override
    public void delete(long id) {
        taskRepository.delete(id);
    }
}
