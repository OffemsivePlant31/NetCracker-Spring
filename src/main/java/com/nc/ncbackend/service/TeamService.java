package com.nc.ncbackend.service;

import com.nc.ncbackend.pojo.GameInstance;
import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.pojo.Team;

import java.util.List;

public interface TeamService {

    Team findOne(long id);
    List<Team> findAll();
    void save(Team team);
    void delete(Team team);
    void deleteAll();

    Team findByPersonAndGameInstance(Person person, GameInstance game);
    List<Team> findByPerson(Person person);
    Team findFirstByPerson(Person person);
    Team findByGame_IdAndUsers_Id(long gameId, long personId);
}
