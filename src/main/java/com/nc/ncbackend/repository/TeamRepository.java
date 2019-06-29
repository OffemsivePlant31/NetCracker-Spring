package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.GameInstance;
import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.pojo.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Long> {

    @Query("select t from Team t where :person in (t.users) and t.game = :game")
    Team findByPersonAndGameInstance(@Param("person")Person person, @Param("game")GameInstance game);

    @Query("select t from Team t left join t.game g left join t.users u " +
            "where :person = u and g.endDate is null " +
            "and g.startDate < :date " +
            "ORDER BY t.gameStarted ASC")
    List<Team> findByPerson(@Param("person")Person person,
                            @Param("date")LocalDateTime startDate);

    Team findByGame_IdAndUsers_Id(long gameId, long personId);

}
