package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {


    Person findByEmail(String email);
}
