package com.nc.ncbackend.service;

import com.nc.ncbackend.pojo.GameInstance;

import java.util.List;

public interface GameInstanceService {

    List<GameInstance> findAll();
    GameInstance findOne(long id);
    void save(GameInstance gameInstance);
    void deleteAll();
    List<GameInstance> findAllByEndDateIsNull();
    List<GameInstance> findAllByEndDateIsNullAndPublicGameIsTrue();
}
