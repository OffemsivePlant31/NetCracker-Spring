package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.GameTask;
import com.nc.ncbackend.pojo.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameTaskRepository extends CrudRepository<GameTask, Long> {

    @Query("select gt from GameTask gt  where gt.team = :team")
    List<GameTask> findByTeam(@Param("team")Team team);

    @Query("select t from GameTask t where t.team = :team and t.timeDone is null "+
            "and not exists (select 1 from GameTask t1 where t in (t1.nextTasks) and t1.timeDone is not null )")
    GameTask findNextByTeam(@Param("team")Team team);

    Long countByTeamAndTimeDoneIsNotNull(Team team);

}
