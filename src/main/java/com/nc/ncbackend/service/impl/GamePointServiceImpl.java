package com.nc.ncbackend.service.impl;

import com.nc.ncbackend.pojo.GamePoint;
import com.nc.ncbackend.repository.GamePointRepository;
import com.nc.ncbackend.service.GamePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamePointServiceImpl implements GamePointService {

    @Autowired
    GamePointRepository gamePointRepository;

    @Override
    public void save(GamePoint gp){
        gamePointRepository.save(gp);
    }

    @Override
    public List<GamePoint> findAll(){
        return (List<GamePoint>) gamePointRepository.findAll();
    }

    @Override
    public void deleteAll(){
        gamePointRepository.deleteAll();
    }

    @Override
    public void delete(long id){
        gamePointRepository.delete(id);
    }

    @Override
    public GamePoint find(long id){
        return gamePointRepository.findOne(id);
    }

    @Override
    public GamePoint findLastByTask(String name){
        return gamePointRepository.findTopByNameOrderByIdDesc(name);
    }
}
