package com.nc.ncbackend.service.impl;

import com.nc.ncbackend.pojo.GameInstance;
import com.nc.ncbackend.repository.GameInstanceRepository;
import com.nc.ncbackend.service.GameInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameInstanceServiceImpl implements GameInstanceService{

    @Autowired
    GameInstanceRepository gameInstanceRepository;

    @Override
    public List<GameInstance> findAll() {
        return (List<GameInstance>) gameInstanceRepository.findAll();
    }

    @Override
    public GameInstance findOne(long id) {
        return gameInstanceRepository.findOne(id);
    }

    @Override
    public void save(GameInstance gameInstance) {
        gameInstanceRepository.save(gameInstance);
    }

    @Override
    public void deleteAll() {
        gameInstanceRepository.deleteAll();
    }

    @Override
    public List<GameInstance> findAllByEndDateIsNull() {
        return gameInstanceRepository.findAllByEndDateIsNull();
    }

    @Override
    public List<GameInstance> findAllByEndDateIsNullAndPublicGameIsTrue() {
        return gameInstanceRepository.findAllByEndDateIsNullAndPublicGameIsTrue();
    }


}
