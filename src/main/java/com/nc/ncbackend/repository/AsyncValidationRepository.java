package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.AsyncValidation;
import com.nc.ncbackend.pojo.GameTask;
import com.nc.ncbackend.pojo.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AsyncValidationRepository extends CrudRepository<AsyncValidation, Long> {

    long countByGameTask(GameTask gameTask);

    AsyncValidation findByGameTaskAndState(GameTask gameTask, int state);

    List<AsyncValidation> findByGameTaskOrderByIdDesc(GameTask gameTask);

    List<AsyncValidation> findByAuditorAndState(Person overseer, int state);

}
