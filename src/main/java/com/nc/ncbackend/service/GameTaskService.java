package com.nc.ncbackend.service;

import com.nc.ncbackend.pojo.GameTask;
import com.nc.ncbackend.pojo.Team;

import java.util.List;

public interface GameTaskService {

    GameTask findOne(long id);
    List<GameTask> findAll();
    void save(GameTask gameTask);
    void delete(GameTask gameTask);
    void deleteAll();

    List<GameTask> findByTeam(Team team);
    GameTask findNextByTeam(Team team);

    Long countByTeamAndTimeDoneIsNotNull(Team team);

}
