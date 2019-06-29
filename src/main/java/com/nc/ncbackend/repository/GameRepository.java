package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.Game;
import com.nc.ncbackend.pojo.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {


    List<Game> findByName(String name);
    List<Game> findByAuthor(Person person);
    List<Game> findByPublicTemplate(boolean isPublic);
    Game findTopByAuthorOrderByIdDesc(Person author);
}
