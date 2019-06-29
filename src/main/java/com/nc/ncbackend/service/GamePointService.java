package com.nc.ncbackend.service;

import com.nc.ncbackend.dto.GamePointDto;
import com.nc.ncbackend.pojo.GamePoint;
import com.nc.ncbackend.repository.GamePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface GamePointService {


    void save(GamePoint gp);

    List<GamePoint> findAll();

    void deleteAll();

    void delete(long id);

    GamePoint find(long id);

    GamePoint findLastByTask(String name);
}
