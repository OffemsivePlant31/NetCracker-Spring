package com.nc.ncbackend.service.impl;

import com.nc.ncbackend.pojo.GameTask;
import com.nc.ncbackend.pojo.Team;
import com.nc.ncbackend.repository.GameTaskRepository;
import com.nc.ncbackend.service.GameTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameTaskServiceImpl implements GameTaskService {

    @Autowired
    GameTaskRepository gameTaskRepository;

    @Override
    public GameTask findOne(long id) {
        return gameTaskRepository.findOne(id);
    }

    @Override
    public List<GameTask> findAll() {
        return (List<GameTask>) gameTaskRepository.findAll();
    }

    @Override
    public void save(GameTask gameTask) {
        gameTaskRepository.save(gameTask);
    }

    @Override
    public void delete(GameTask gameTask) {
        gameTaskRepository.delete(gameTask);
    }

    @Override
    public void deleteAll() {
        gameTaskRepository.deleteAll();
    }

    @Override
    public List<GameTask> findByTeam(Team team) {
        return gameTaskRepository.findByTeam(team);
    }

    @Override
    public GameTask findNextByTeam(Team team) {
        return gameTaskRepository.findNextByTeam(team);
    }

    @Override
    public Long countByTeamAndTimeDoneIsNotNull(Team team) {
        return gameTaskRepository.countByTeamAndTimeDoneIsNotNull(team);
    }


}
