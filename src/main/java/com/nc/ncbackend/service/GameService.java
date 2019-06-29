package com.nc.ncbackend.service;

import com.nc.ncbackend.pojo.Game;
import com.nc.ncbackend.pojo.Person;

import java.util.List;

public interface GameService {

    public void save(Game game);

    public List<Game> findAll();

    public Game findOne(long id);

    public List<Game> findByName(String name);

    public void deleteAll();

    public void delete(long id);

    public void delete(Game game);

    public List<Game> findByAuthor(Person person);

    public List<Game> findAllPublicTemplates();

    public Game findLastByAuthor(Person person);
}
