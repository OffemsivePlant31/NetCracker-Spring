package com.nc.ncbackend.service.impl;

import com.nc.ncbackend.pojo.GameInstance;
import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.pojo.Team;
import com.nc.ncbackend.repository.TeamRepository;
import com.nc.ncbackend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRepository teamRepository;

    @Override
    public Team findOne(long id) {
        return teamRepository.findOne(id);
    }

    @Override
    public List<Team> findAll() {
        return (List<Team>) teamRepository.findAll();
    }

    @Override
    public void save(Team team) {
        teamRepository.save(team);
    }

    @Override
    public void delete(Team team) {
        teamRepository.delete(team);
    }

    @Override
    public void deleteAll() {
        teamRepository.deleteAll();
    }

    @Override
    public Team findByPersonAndGameInstance(Person person, GameInstance game) {
        return teamRepository.findByPersonAndGameInstance(person, game);
    }

    @Override
    public List<Team> findByPerson(Person person) {
        return teamRepository.findByPerson(person, LocalDateTime.now().plusHours(1));
    }

    @Override
    public Team findFirstByPerson(Person person) {

        List<Team> teams = teamRepository.findByPerson(person, LocalDateTime.now().plusHours(1));
        if(teams.size() > 0){
            return teams.get(0);
        }else{
            return null;
        }

    }

    @Override
    public Team findByGame_IdAndUsers_Id(long gameId, long personId){
        return teamRepository.findByGame_IdAndUsers_Id(gameId, personId);
    }
}
