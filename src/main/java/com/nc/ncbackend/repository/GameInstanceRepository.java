package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.GameInstance;
import com.nc.ncbackend.pojo.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameInstanceRepository extends CrudRepository<GameInstance, Long> {

    @Query("select g from GameInstance g where exists " +
            "(select 1 from g.teams t left join t.users u where u = :person)")
    GameInstance findCurrentGame(@Param("person") Person person);

    List<GameInstance> findAllByEndDateIsNull();

    List<GameInstance> findAllByEndDateIsNullAndPublicGameIsTrue();

    GameInstance findTop1ByOverseerAndEndDateIsNullOrderByStartDate(Person overseer);
}
