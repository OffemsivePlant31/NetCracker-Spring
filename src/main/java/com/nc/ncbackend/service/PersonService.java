package com.nc.ncbackend.service;

import com.nc.ncbackend.pojo.Person;

import java.util.List;

public interface PersonService {

    List<Person> findAll();
    Person findOne(long id);
    void save(Person person);
    void delete(Person person);
    void deleteAll();

    Person findByEmail(String email);

}
