package com.nc.ncbackend.controller;


import com.nc.ncbackend.pojo.GamePoint;
import com.nc.ncbackend.service.GamePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
public class GamePointController {

    @Autowired
    GamePointService gamePointService;



    @PostMapping(value = { "", "/" })
    public List<GamePoint> post(@RequestBody GamePoint gp){

        gamePointService.save(gp);
        return gamePointService.findAll();
    }

    @DeleteMapping("/clear")
    public List<GamePoint> clear(){

        gamePointService.deleteAll();
        return gamePointService.findAll();
    }

    @DeleteMapping("/{id}")
    public List<GamePoint> delete(@PathVariable long id){

        gamePointService.delete(id);
        return gamePointService.findAll();
    }

    @GetMapping(value = { "", "/" })
    public List<GamePoint> findAll(){

        return gamePointService.findAll();
    }

//    @GetMapping("/{gameId}")
//    public List<GamePoint> getGamePoints(@PathVariable long gameId){
//
//    }

}
