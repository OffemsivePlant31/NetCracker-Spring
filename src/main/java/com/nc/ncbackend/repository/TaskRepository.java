package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.Game;
import com.nc.ncbackend.pojo.Task;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByGame(Game game);
    Task findTopByGameOrderByIdDesc(Game game);
}
