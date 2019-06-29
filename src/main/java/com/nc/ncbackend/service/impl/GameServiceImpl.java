package com.nc.ncbackend.service.impl;

import com.nc.ncbackend.pojo.Game;
import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.repository.GameRepository;
import com.nc.ncbackend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;

    @Override
    public void save(Game game) {
        gameRepository.save(game);
    }

    @Override
    public List<Game> findAll() {
        return (List<Game>) gameRepository.findAll();
    }

    @Override
    public Game findOne(long id) {
        return gameRepository.findOne(id);
    }

    @Override
    public List<Game> findByName(String name) {
        return gameRepository.findByName(name);
    }

    @Override
    public void deleteAll() {
        gameRepository.deleteAll();
    }

    @Override
    public void delete(long id) {
        gameRepository.delete(id);
    }

    @Override
    public void delete(Game game) {
        gameRepository.delete(game);
    }

    @Override
    public List<Game> findByAuthor(Person person){
        return gameRepository.findByAuthor(person);
    }

    @Override
    public List<Game> findAllPublicTemplates(){
        return gameRepository.findByPublicTemplate(true);
    }

    @Override
    public Game findLastByAuthor(Person person){
        return gameRepository.findTopByAuthorOrderByIdDesc(person);
    }
}
