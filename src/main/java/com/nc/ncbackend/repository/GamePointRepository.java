package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.GamePoint;
import org.springframework.data.repository.CrudRepository;

public interface GamePointRepository extends CrudRepository<GamePoint, Long> {
    GamePoint findTopByNameOrderByIdDesc(String name);
}
