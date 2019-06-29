package com.nc.ncbackend.controller;


import com.nc.ncbackend.dto.GamePointDto;
import com.nc.ncbackend.dto.GameTemplateDto;
import com.nc.ncbackend.pojo.Game;
import com.nc.ncbackend.pojo.GameInstance;
import com.nc.ncbackend.pojo.Task;
import com.nc.ncbackend.repository.GameInstanceRepository;
import com.nc.ncbackend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    GameInstanceRepository gameInstanceRepository;

    @GetMapping("/")
    public List<GameTemplateDto> findAll(){
        List<Game> games = gameService.findAll();
        List<GameTemplateDto> result = new ArrayList<>();
        for(Game game : games){
            result.add(new GameTemplateDto(game));
        }
        return result;
    }

    @GetMapping("/{gameid}/point")
    public ResponseEntity<List<GamePointDto>> findAllPoints(
            @PathVariable("gameid") long gameid
    ){
        // TODO: 12.12.2017 Костыль: На вход приходит id инстанса, а должен id игры
        GameInstance gameInstance = gameInstanceRepository.findOne(gameid);
        Game game = gameInstance.getGame();
        if(game == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<GamePointDto> result = new ArrayList<>();
        for(Task task : game.getTasks()){
            result.add(new GamePointDto(task.getPoint()));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
